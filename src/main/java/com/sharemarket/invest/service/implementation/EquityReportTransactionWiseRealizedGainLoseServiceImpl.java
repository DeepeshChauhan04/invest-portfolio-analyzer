package com.sharemarket.invest.service.implementation;

import com.sharemarket.invest.dao.EquityPriceHistoryDao;
import com.sharemarket.invest.dao.EquityTransactionDao;
import com.sharemarket.invest.dto.request.PortfolioHoldingsResponse;
import com.sharemarket.invest.dto.request.SecuritiesRequest;
import com.sharemarket.invest.entity.EquityPriceHistory;
import com.sharemarket.invest.entity.EquityTransaction;
import com.sharemarket.invest.service.abstraction.EquityReportTransactionWiseRealizedGainLoseService;
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
public class EquityReportTransactionWiseRealizedGainLoseServiceImpl implements EquityReportTransactionWiseRealizedGainLoseService {

    private final EquityTransactionDao equityTransactionDao;
    private final EquityPriceHistoryDao equityPriceHistoryDao;

    public PortfolioHoldingsResponse getRealizedGains(SecuritiesRequest request) {

        List<EquityTransaction> transactions =
                equityTransactionDao.findAllRequiredSecurities(
                        request.getStartDate(),
                        request.getEndDate(),
                        request.getPortfolioIds(),
                        getOrNull(request.getIsinList()),
                        getOrNull(request.getSeriesList()),
                        getOrNull(request.getCodeList())
                );
        Set<String> isinList = new HashSet<>();
        Set<String> codeList = new HashSet<>();
        Set<String> seriesList = new HashSet<>();

        Map<String, List<EquityTransaction>> securityTransaction = transactions
                .stream()
                .map(tran -> addIsinCodeSeries(tran, isinList, codeList, seriesList))
                .collect(Collectors.groupingBy(tran ->
                        getSecurityGroupKey(tran.getIsin(), tran.getCode(), tran.getSeries())));
        log.info("SecurityTransaction {}", securityTransaction.size());

        Map<String, BigDecimal> securityClosePriceMap = getSecurityClosePriceMap(isinList,
                codeList, seriesList, LocalDate.now());


        List<PortfolioHoldingsResponse.PortfolioWiseResponse> portfolioWiseList =
                transactions.stream()
                        .collect(Collectors.groupingBy(EquityTransaction::getPortfolioId))
                        .entrySet()
                        .stream()
                        .map(portfolioEntry -> getPortfolioWiseList(portfolioEntry, securityClosePriceMap))
                        .toList();

        return PortfolioHoldingsResponse.builder()
                .details(portfolioWiseList)
                .build();
    }

    private PortfolioHoldingsResponse.PortfolioWiseResponse getPortfolioWiseList(Map.Entry<Long, List<EquityTransaction>> portfolioEntry,
                                                                                 Map<String, BigDecimal> securityClosePriceMap) {

        Long portfolioId = portfolioEntry.getKey();

        List<PortfolioHoldingsResponse.HoldingSecurityDTO> holdingSecurities =
                portfolioEntry.getValue().stream()
                        .collect(Collectors.groupingBy(tran ->
                                getSecurityGroupKey(tran.getIsin(), tran.getCode(), tran.getSeries())))
                        .entrySet()
                        .stream()
                        .map(securityEntry -> getSecurityResponse(
                                securityEntry.getValue(),
                                securityClosePriceMap.getOrDefault(securityEntry.getKey(), BigDecimal.ZERO)
                        ))
                        .toList();
        return PortfolioHoldingsResponse.PortfolioWiseResponse.builder()
                .portfolioId(portfolioId)
                .holdingSecurityDTO(holdingSecurities)
                .build();
    }

    private Set<String> getOrNull(Set<String> list) {
        return Objects.isNull(list) || list.isEmpty() ? null : list;
    }

    private EquityTransaction addIsinCodeSeries(EquityTransaction tran, Set<String> isinList,
                                                Set<String> codeList, Set<String> seriesList) {

        isinList.add(tran.getIsin());
        codeList.add(tran.getCode());
        seriesList.add(tran.getSeries());
        return tran;
    }

    public PortfolioHoldingsResponse.HoldingSecurityDTO getSecurityResponse(List<EquityTransaction> transactions,
                                                                            BigDecimal closePrice) {

        Map<String, List<EquityTransaction>> tranTypeTransactionMap = transactions.stream()
                .collect(Collectors.groupingBy(EquityTransaction::getTransactionType));

        List<EquityTransaction> buyFifo = tranTypeTransactionMap.getOrDefault("Buy", Collections.emptyList())
                .stream().sorted(Comparator.comparing(EquityTransaction::getTransactionDate)).toList();
        List<EquityTransaction> buyLifo = tranTypeTransactionMap.getOrDefault("Buy", Collections.emptyList()).stream()
                .sorted(Comparator.comparing(EquityTransaction::getTransactionDate).reversed()).toList();

        List<EquityTransaction> buyByPrice = tranTypeTransactionMap.getOrDefault("Buy", Collections.emptyList())
                .stream().sorted(Comparator.comparing(EquityTransaction::getPricePrUnit)).toList();

        List<EquityTransaction> sellTransactions = tranTypeTransactionMap.getOrDefault("Sell", Collections.emptyList())
                .stream()
                .sorted(Comparator.comparing(EquityTransaction::getTransactionDate))
                .toList();

        List<PortfolioHoldingsResponse.TransactionDetail> details = sellTransactions.stream()
                .map(sellTx -> getTransactionDetails(getRequiredBuyTrans(buyFifo, buyLifo, buyByPrice,
                        sellTx.getValuationMethod()), sellTx))
                .flatMap(List::stream)
                .toList();

        return new PortfolioHoldingsResponse.HoldingSecurityDTO(
                transactions.get(0).getIsin(),
                transactions.get(0).getSeries(),
                transactions.get(0).getCode(),
                details
        );
    }

    private List<PortfolioHoldingsResponse.TransactionDetail> getTransactionDetails(List<EquityTransaction> buyTrans,
                                                                                    EquityTransaction sellTx) {
        return buyTrans.stream()
                .filter(tran -> sellTx.getQuantity() > 0 && tran.getQuantity() > 0)
                .map(tran -> getTransactionDetail(tran, sellTx))
                .toList();

    }

    private PortfolioHoldingsResponse.TransactionDetail getTransactionDetail(EquityTransaction tran, EquityTransaction sellTx) {

        PortfolioHoldingsResponse.TransactionDetail detail = PortfolioHoldingsResponse.TransactionDetail.builder()
                .sellPrice(sellTx.getPricePrUnit())
                .buyPrice(tran.getPricePrUnit())
                .buyTransactionDate(tran.getTransactionDate())
                .sellTransactionDate(sellTx.getTransactionDate())
                .build();

        if (tran.getQuantity() >= sellTx.getQuantity()) {
            detail.setSellQuantity(sellTx.getQuantity());
            tran.setQuantity(tran.getQuantity() - sellTx.getQuantity());
            detail.setSellValue(sellTx.getPricePrUnit().multiply(BigDecimal.valueOf(detail.getSellQuantity())));
            detail.setCostValue(tran.getPricePrUnit().multiply(BigDecimal.valueOf(detail.getSellQuantity())));

            sellTx.setQuantity(0);
        } else {
            detail.setSellQuantity(tran.getQuantity());
            sellTx.setQuantity(sellTx.getQuantity() - tran.getQuantity());
            detail.setSellValue(sellTx.getPricePrUnit().multiply(BigDecimal.valueOf(detail.getSellQuantity())));
            detail.setCostValue(tran.getPricePrUnit().multiply(BigDecimal.valueOf(detail.getSellQuantity())));
            tran.setQuantity(0);
        }

        detail.setRealizedGainLoss((detail.getSellPrice().subtract(detail.getBuyPrice()))
                .multiply(BigDecimal.valueOf(detail.getSellQuantity())));

        return detail;
    }

    private List<EquityTransaction> getRequiredBuyTrans(List<EquityTransaction> buyFifo, List<EquityTransaction> buyLifo,
                                                        List<EquityTransaction> buyByPrice, String valuationMethod) {

        if (valuationMethod.equalsIgnoreCase("fifo"))
            return buyFifo;

        if (valuationMethod.equalsIgnoreCase("lifo"))
            return buyLifo;

        return buyByPrice;
    }

    private Map<String, BigDecimal> getSecurityClosePriceMap(Set<String> isinList, Set<String> codeList,
                                                             Set<String> seriesList, LocalDate endDate) {
        Map<String, BigDecimal> securityClosePrice = equityPriceHistoryDao
                .findLatestClosePrice(isinList, codeList, seriesList, endDate)
                .stream()
                .collect(Collectors.toMap(price -> getSecurityGroupKey(price.getIsin(), price.getCode(),
                        price.getSeries()), EquityPriceHistory::getClosePrice, (p1, p2) -> p1));

        log.info("Eq security close price map : {}", securityClosePrice.size());
        return securityClosePrice;
    }


    private String getSecurityGroupKey(String isin, String code, String series) {

        return isin.concat("-").concat(code).concat("-").concat(series);

    }
}


