package com.sharemarket.invest.service.implementation;

import com.sharemarket.invest.dto.request.SecuritiesRequest;
import com.sharemarket.invest.dto.response.*;
import com.sharemarket.invest.service.abstraction.MutualFundRealizationGainLossService;
import com.sharemarket.invest.service.abstraction.MutualFundSecuritiesWiseRealizeGainLossService;
import com.sharemarket.invest.service.abstraction.MutualFundTransactionWiseUnrealizedGainLoseService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MutualFundSecuritiesWiseRealizeGainLossServiceImpl implements MutualFundSecuritiesWiseRealizeGainLossService {

    private final MutualFundTransactionWiseUnrealizedGainLoseService mutualFundTransactionWiseUnrealizedGainLoseService;
    private final MutualFundRealizationGainLossService securities;

    @Override
    public MutualFundSecuritiesRealizedGainLossResponse getSecuritiesWiseRealizedGainLoss(SecuritiesRequest request) {
        MutualFundRealizeGainLossResponse response = securities.getTransactionWiseRealizationGainLoss(request);

        List<MutualFundSecuritiesRealizedGainLossResponse.PortfolioWiseResponse> portfolios = response.getDetails().stream()
                .map(portfolioWiseResponse ->
                        MutualFundSecuritiesRealizedGainLossResponse.PortfolioWiseResponse
                                .builder()
                                .portfolioId(portfolioWiseResponse.getPortfolioId())
                                .securities(getSecurities(portfolioWiseResponse.getHoldingSecurityDTO())
                                        ).build()
                )
                .toList();
        return MutualFundSecuritiesRealizedGainLossResponse.builder()
                .details(portfolios)
                .build();

    }
    private List<MutualFundSecuritiesRealizedGainLossResponse.Securities> getSecurities(List<MutualFundRealizeGainLossResponse.HoldingSecurityDTO> holdingSecurities) {

        return holdingSecurities.stream()
                .map(this::getSecurity)
                .toList();

    }

    private MutualFundSecuritiesRealizedGainLossResponse.Securities getSecurity(MutualFundRealizeGainLossResponse.HoldingSecurityDTO securities) {

        MutualFundSecuritiesRealizedGainLossResponse.Securities security = MutualFundSecuritiesRealizedGainLossResponse.Securities.builder()
                .isin(securities.getIsin())
                .code(securities.getCode())
                .build();

        BigDecimal costValue = securities.getDetails().stream()
                .map(MutualFundRealizeGainLossResponse.TransactionDetail::getCostValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalRealisedGainLoss = securities.getDetails()
                .stream()
                .map(MutualFundRealizeGainLossResponse.TransactionDetail::getRealizedGainLoss)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        security.setRealizingGainLoss(totalRealisedGainLoss)
                .setCostValue(costValue);

        return security;
    }


    @Override
    public MutualFundSecuritiesUnRealizedGainLossResponse getSecuritiesWiseUnRealizedGainLoss(SecuritiesRequest request) {


        MutualFundUnRealizeGainLossResponse response = mutualFundTransactionWiseUnrealizedGainLoseService.getTransactionWiseUnRealizationGainLoss(request);

        List<MutualFundSecuritiesUnRealizedGainLossResponse.PortfolioWiseResponse> listOfPortfolioDetails = response.getDetails()
                .stream()
                .map(portfolioWiseResponse ->
                        MutualFundSecuritiesUnRealizedGainLossResponse.PortfolioWiseResponse.builder()
                                .portfolioId(portfolioWiseResponse.getPortfolioId())
                                .securities(getSecuritiesUnrealized(portfolioWiseResponse.getHoldingSecurityDTO()))
                                .build())
                .toList();

        return MutualFundSecuritiesUnRealizedGainLossResponse.builder()
                .details(listOfPortfolioDetails)
                .build();
    }

    private List<MutualFundSecuritiesUnRealizedGainLossResponse.Securities> getSecuritiesUnrealized(List<MutualFundUnRealizeGainLossResponse.HoldingSecurityDTO> holdingSecurities) {
        return holdingSecurities.stream()
                .map(this::getUnrealizedSecurity).toList();
    }

    private MutualFundSecuritiesUnRealizedGainLossResponse.Securities getUnrealizedSecurity(MutualFundUnRealizeGainLossResponse.HoldingSecurityDTO holdingSecurities) {


        MutualFundSecuritiesUnRealizedGainLossResponse.Securities responseUnrealizedGainLoss = MutualFundSecuritiesUnRealizedGainLossResponse.Securities.builder()
                .code(holdingSecurities.getCode())
                .isin(holdingSecurities.getIsin())
                .build();


        Pair<BigDecimal, BigDecimal> costValueUnrealisedValue = holdingSecurities.getDetail().stream()
                .collect(Collectors.teeing(
                        Collectors.mapping(MutualFundUnRealizeGainLossResponse.TransactionDetail::getCostValue,
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)),
                        Collectors.mapping(MutualFundUnRealizeGainLossResponse.TransactionDetail::getUnrealizedGainLoss,
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)),
                        Pair::of
                ));

        responseUnrealizedGainLoss.setCostValue(costValueUnrealisedValue.getLeft())
                .setUnrealizingGainLoss(costValueUnrealisedValue.getRight());

        return responseUnrealizedGainLoss;
    }
}