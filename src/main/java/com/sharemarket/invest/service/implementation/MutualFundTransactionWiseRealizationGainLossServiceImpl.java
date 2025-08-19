package com.sharemarket.invest.service.implementation;

import com.sharemarket.invest.dao.MutualFundReportDao;
import com.sharemarket.invest.dto.request.SecuritiesRequest;
import com.sharemarket.invest.dto.response.MutualFundRealizeGainLossResponse;
import com.sharemarket.invest.entity.MutualFundPriceHistory;
import com.sharemarket.invest.entity.MutualFundTransaction;
import com.sharemarket.invest.service.abstraction.MutualFundRealizationGainLossService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MutualFundTransactionWiseRealizationGainLossServiceImpl implements MutualFundRealizationGainLossService {
    private final MutualFundReportDao mutualFundReportDao;
    @Override
    public MutualFundRealizeGainLossResponse getTransactionWiseRealizationGainLoss(SecuritiesRequest request) {
        List<MutualFundTransaction> list = mutualFundReportDao.findAllRequiredSecurities(request.getStartDate(), request.getEndDate(), request.getPortfolioIds(), request.getIsinList(), request.getCodeList());

        Map<String, List<MutualFundTransaction>> securityTransaction = mutualFundReportDao
                .findAllRequiredSecurities(request.getStartDate(),
                        request.getEndDate(), request.getPortfolioIds(),
                        request.getIsinList(), request.getCodeList())
                .stream()
                .collect(Collectors.groupingBy(fund ->
                        getSecurityGroupKey(fund.getIsin(), fund.getCode())));

        log.info("SecurityTransaction {}", securityTransaction.size());

        Map<String, BigDecimal> securityClosePriceMap = getSecurityClosePriceMap(request.getIsinList(),
                request.getCodeList(), request.getEndDate());

        List<MutualFundRealizeGainLossResponse.PortfolioWiseResponse> portfolioDetails = list.stream().collect(Collectors.groupingBy(MutualFundTransaction::getPortfolioId))
                .entrySet()
                .stream().map(longListEntry ->
                        getPortfolioWiseList(longListEntry, securityClosePriceMap)).toList();


        List<MutualFundRealizeGainLossResponse.HoldingSecurityDTO> holdingSecurities = securityTransaction
                .entrySet()
                .stream()
                .map(entry -> getSecurityResponse(
                        entry.getValue(),
                        securityClosePriceMap.getOrDefault(entry.getKey(), BigDecimal.ZERO)
                ))
                .toList();

        log.info("MutualFund holding securities count: {}", holdingSecurities.size());

        return MutualFundRealizeGainLossResponse.builder()
                .details(portfolioDetails)
                .build();

    }

    private MutualFundRealizeGainLossResponse.PortfolioWiseResponse getPortfolioWiseList(Map.Entry<Long, List<MutualFundTransaction>> longListEntry, Map<String, BigDecimal> securityClosePriceMap) {
        Long portfolioId = longListEntry.getKey();
        List<MutualFundRealizeGainLossResponse.HoldingSecurityDTO> record = longListEntry.getValue().stream().collect(Collectors.groupingBy(mft ->
                        getSecurityGroupKey(mft.getIsin(), mft.getCode()))).entrySet()
                .stream().map(stringListEntry -> getSecurityResponse(
                        stringListEntry.getValue(),
                        securityClosePriceMap.getOrDefault(stringListEntry.getKey(), BigDecimal.ZERO)
                ))
                .toList();


        return MutualFundRealizeGainLossResponse.PortfolioWiseResponse.builder()
                .portfolioId(portfolioId)
                .holdingSecurityDTO(record)
                .build();
    }

    public MutualFundRealizeGainLossResponse.HoldingSecurityDTO getSecurityResponse(List<MutualFundTransaction> transactions,
                                                                                    BigDecimal closePrice) {

        Map<String, List<MutualFundTransaction>> tranTypeTransactionMap = transactions.stream()
                .collect(Collectors.groupingBy(MutualFundTransaction::getTransactionType));

        List<MutualFundTransaction> buyFifo = tranTypeTransactionMap.getOrDefault("Buy", Collections.emptyList())
                .stream().sorted(Comparator.comparing(MutualFundTransaction::getTransactionDate)).toList();
        List<MutualFundTransaction> buyLifo = tranTypeTransactionMap.getOrDefault("Buy", Collections.emptyList()).stream()
                .sorted(Comparator.comparing(MutualFundTransaction::getTransactionDate).reversed()).toList();
        List<MutualFundTransaction> buyByPrice = tranTypeTransactionMap.getOrDefault("Buy", Collections.emptyList())
                .stream().sorted(Comparator.comparing(MutualFundTransaction::getNavPrUnite)).toList();

        List<MutualFundTransaction> sellTransactions = tranTypeTransactionMap.getOrDefault("Sell", Collections.emptyList())
                .stream()
                .sorted(Comparator.comparing(MutualFundTransaction::getTransactionDate))
                .toList();

        List<MutualFundRealizeGainLossResponse.TransactionDetail> details = sellTransactions.stream()
                .map(sellTx -> getTransactionDetails(getRequiredBuyTrans(buyFifo, buyLifo, buyByPrice,
                        sellTx.getValuationMethod()), sellTx))
                .flatMap(List::stream)
                .toList();

        return  MutualFundRealizeGainLossResponse.HoldingSecurityDTO.builder()
                .isin(transactions.get(0).getIsin())
                .code(transactions.get(0).getCode())
                .details(details)
                .build();
    }

    private List<MutualFundRealizeGainLossResponse.TransactionDetail> getTransactionDetails(List<MutualFundTransaction> buyTrans,
                                                                                            MutualFundTransaction sellTx) {
        return buyTrans.stream()
                .filter(tran -> sellTx.getUnits() > 0 && tran.getUnits() > 0)
                .map(tran -> getTransactionDetail(tran, sellTx))
                .toList();

    }

    private MutualFundRealizeGainLossResponse.TransactionDetail getTransactionDetail(MutualFundTransaction tran, MutualFundTransaction sellTx) {


        MutualFundRealizeGainLossResponse.TransactionDetail detail = MutualFundRealizeGainLossResponse.TransactionDetail.builder()
                .sellPrice(sellTx.getNavPrUnite())
                .buyTransactionDate(tran.getTransactionDate())
                .buyPrice(tran.getNavPrUnite())
                .sellTransactionDate(sellTx.getTransactionDate())
                .build();

        if (tran.getUnits() >= sellTx.getUnits()) {
            detail.setSellQuantity(sellTx.getUnits());
            tran.setUnits((tran.getUnits() - sellTx.getUnits()));
            detail.setSellValue(sellTx.getNavPrUnite().multiply(BigDecimal.valueOf(detail.getSellQuantity())));
            detail.setCostValue(tran.getNavPrUnite().multiply(BigDecimal.valueOf(detail.getSellQuantity())));

            sellTx.setUnits(0);
        } else {
            detail.setSellQuantity(tran.getUnits());
            sellTx.setUnits(sellTx.getUnits() - tran.getUnits());
            detail.setSellValue(sellTx.getNavPrUnite().multiply(BigDecimal.valueOf(detail.getSellQuantity())));
            detail.setCostValue(tran.getNavPrUnite().multiply(BigDecimal.valueOf(detail.getSellQuantity())));


            tran.setUnits(0);
        }

        detail.setRealizedGainLoss((detail.getSellPrice().subtract(detail.getBuyPrice()))
                .multiply(BigDecimal.valueOf(detail.getSellQuantity())));

        return detail;
    }

    private List<MutualFundTransaction> getRequiredBuyTrans(List<MutualFundTransaction> buyFifo, List<MutualFundTransaction> buyLifo,
                                                            List<MutualFundTransaction> buyByPrice, String valuationMethod) {

        if (valuationMethod.equalsIgnoreCase("fifo"))
            return buyFifo;

        if (valuationMethod.equalsIgnoreCase("lifo"))
            return buyLifo;

        return buyByPrice;
    }

    private Map<String, BigDecimal> getSecurityClosePriceMap(Set<String> isinList, Set<String> codeList,
                                                             LocalDate endDate) {
        Map<String, BigDecimal> securityClosePrice = mutualFundReportDao
                .findLatestClosePrice(isinList, codeList, endDate)
                .stream()
                .collect(Collectors.toMap(price -> getSecurityGroupKey(price.getIsin(), price.getCode())
                        , MutualFundPriceHistory::getNav, (p1, p2) -> p1));
        log.info("Mf security close price map : {}", securityClosePrice.size());
        return securityClosePrice;
    }

    private String getSecurityGroupKey(String isin, String code) {

        return isin.concat("-").concat(code);

    }
}


