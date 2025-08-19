package com.sharemarket.invest.service.implementation;

import com.sharemarket.invest.dto.request.PortfolioHoldingsResponse;
import com.sharemarket.invest.dto.request.SecuritiesRequest;
import com.sharemarket.invest.dto.request.SecuritiesResponse;
import com.sharemarket.invest.dto.response.SecuritiesRealizedGainLossResponse;
import com.sharemarket.invest.dto.response.SecuritiesUnRealizedGainLossResponse;
import com.sharemarket.invest.service.abstraction.EquityReportTransactionWiseRealizedGainLoseService;
import com.sharemarket.invest.service.abstraction.EquityReportTransactionWiseUnRealizedGainLoseService;
import com.sharemarket.invest.service.abstraction.EquitySecuritiesWiseGainLossService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EquitySecuritiesWiseGainLossServiceImpl implements EquitySecuritiesWiseGainLossService {
    private final EquityReportTransactionWiseRealizedGainLoseService equityReportTransactionWiseRealizedGainLoseService;
    private final EquityReportTransactionWiseUnRealizedGainLoseService equityReportTransactionWiseUnRealizedGainLoseService;


    @Override
    public SecuritiesRealizedGainLossResponse getSecuritiesWiseRealizedGainLoss(SecuritiesRequest request) {
        PortfolioHoldingsResponse response = equityReportTransactionWiseRealizedGainLoseService.getRealizedGains(request);


        List<SecuritiesRealizedGainLossResponse.PortfolioWiseResponse> portfolios = response.getDetails()
                .stream()
                .map(portfolioWiseResponse ->
                        SecuritiesRealizedGainLossResponse.PortfolioWiseResponse
                                .builder()
                        .portfolioId(portfolioWiseResponse.getPortfolioId())
                        .securities(getSecurities(portfolioWiseResponse.getHoldingSecurityDTO()))
                        .build()
                )
                .toList();


        return SecuritiesRealizedGainLossResponse.builder()
                .details(portfolios)
                .build();
    }


    private List<SecuritiesRealizedGainLossResponse.Securities> getSecurities(List<PortfolioHoldingsResponse.HoldingSecurityDTO> holdingSecurities) {

        return holdingSecurities.stream()
                .map(this::getSecurity)
                .toList();

    }

    private SecuritiesRealizedGainLossResponse.Securities getSecurity(PortfolioHoldingsResponse.HoldingSecurityDTO securities) {

        SecuritiesRealizedGainLossResponse.Securities security = SecuritiesRealizedGainLossResponse.Securities.builder()
                .isin(securities.getIsin())//
                .code(securities.getCode())
                .series(securities.getSeries())
                .build();

        BigDecimal costValue = securities.getDetail().stream()
                .map(PortfolioHoldingsResponse.TransactionDetail::getCostValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalRealisedGainLoss = securities.getDetail()
                .stream()
                .map(PortfolioHoldingsResponse.TransactionDetail::getRealizedGainLoss)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        security.setRealizingGainLoss(totalRealisedGainLoss)
                .setCostValue(costValue);
        return security;
    }

    @Override
    public SecuritiesUnRealizedGainLossResponse getUnrealizedGainLoss(SecuritiesRequest request) {
        SecuritiesResponse response = equityReportTransactionWiseUnRealizedGainLoseService.getHoldSecurities(request);


        List<SecuritiesUnRealizedGainLossResponse.PortfolioWiseResponse> portfolio = response.getDetails()
                .stream()
                .map(portfolioWiseResponse
                        -> SecuritiesUnRealizedGainLossResponse.PortfolioWiseResponse
                        .builder()
                        .portfolioId(portfolioWiseResponse.getPortfolioId())
                        .securities(getSecuritiesUnrealized(portfolioWiseResponse.getHoldingSecurityDTO()))
                        .build()
                )
                .toList();


        return SecuritiesUnRealizedGainLossResponse.builder()
                .details(portfolio)
                .build();
    }

    private List<SecuritiesUnRealizedGainLossResponse.Securities> getSecuritiesUnrealized(List<SecuritiesResponse.HoldingSecurityDTO> holdingSecurities) {
        return holdingSecurities.stream().map(this::getUnrealizedSecurity).toList();
    }

    private SecuritiesUnRealizedGainLossResponse.Securities getUnrealizedSecurity(SecuritiesResponse.HoldingSecurityDTO holdingSecurities) {

        SecuritiesUnRealizedGainLossResponse.Securities responseUnrealizedGainLoss = SecuritiesUnRealizedGainLossResponse.Securities.builder()
                .series(holdingSecurities.getSeries())
                .code(holdingSecurities.getCode())
                .isin(holdingSecurities.getIsin())
                .build();
        BigDecimal castValue = holdingSecurities.getDetail().stream().map(SecuritiesResponse.TransactionDetail::getCostValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal unrealizedGainLoss = holdingSecurities.getDetail().stream().map(SecuritiesResponse.TransactionDetail::getUnrealizedGainLoss)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
                 responseUnrealizedGainLoss.setCostValue(castValue)
                .setUnrRealizingGainLoss(unrealizedGainLoss);

        return responseUnrealizedGainLoss;
    }
}

