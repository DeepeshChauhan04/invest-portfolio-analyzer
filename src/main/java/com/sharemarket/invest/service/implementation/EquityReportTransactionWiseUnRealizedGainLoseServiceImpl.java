package com.sharemarket.invest.service.implementation;

import com.sharemarket.invest.dao.EquityPriceHistoryDao;
import com.sharemarket.invest.dao.EquityTransactionDao;
import com.sharemarket.invest.dto.request.SecuritiesRequest;
import com.sharemarket.invest.dto.request.SecuritiesResponse;
import com.sharemarket.invest.entity.EquityPriceHistory;
import com.sharemarket.invest.entity.EquityTransaction;
import com.sharemarket.invest.service.abstraction.EquityReportTransactionWiseUnRealizedGainLoseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor

public class EquityReportTransactionWiseUnRealizedGainLoseServiceImpl implements EquityReportTransactionWiseUnRealizedGainLoseService {
    private final EquityTransactionDao equityTransactionDao;
    private final EquityPriceHistoryDao equityPriceHistoryDao;


    @Override
    public SecuritiesResponse getHoldSecurities(SecuritiesRequest request) {
        List<EquityTransaction> transactions =
                equityTransactionDao.findAllRequiredSecurities(
                        request.getStartDate(),
                        request.getEndDate(),
                        request.getPortfolioIds(),
                        getOrNull(request.getIsinList()),
                        getOrNull(request.getSeriesList()),
                        getOrNull(request.getCodeList()));

        Set<String> isinList = new HashSet<>();
        Set<String> codeList = new HashSet<>();
        Set<String> seriesList = new HashSet<>();

        Map<String, List<EquityTransaction>> securityTransaction = equityTransactionDao
                .findAllRequiredSecurities(request.getStartDate(), request.getEndDate(), request.getPortfolioIds(),
                        getOrNull(request.getIsinList()), getOrNull(request.getSeriesList()), getOrNull(request.getCodeList()))
                .stream()
                .map(tran -> addIsinCodeSeries(tran, isinList, codeList, seriesList))
                .collect(Collectors.groupingBy(tran ->
                        getSecurityGroupKey(tran.getIsin(), tran.getCode(), tran.getSeries())));

        log.info("SecurityTransaction {}", securityTransaction.size());


        Map<String, BigDecimal> securityClosePriceMap = getSecurityClosePriceMap(isinList,
                codeList, seriesList, LocalDate.now());

        List<SecuritiesResponse.PortfolioWiseResponse> portfolioWiseList =
                transactions.stream()
                        .collect(Collectors.groupingBy(EquityTransaction::getPortfolioId))


                        .entrySet()
                        .stream()
                        .map(portfolioEntry -> getPortfolioWiseList(portfolioEntry, securityClosePriceMap))
                        .toList();
        return SecuritiesResponse.builder()
                .details(portfolioWiseList)
                .build();

    }

    private SecuritiesResponse.PortfolioWiseResponse getPortfolioWiseList(Map.Entry<Long, List<EquityTransaction>> portfolioEntry, Map<String, BigDecimal> securityClosePriceMap) {
        {
            Long portfolioId = portfolioEntry.getKey();
            List<SecuritiesResponse.HoldingSecurityDTO> holdingSecurities = portfolioEntry
                    .getValue()
                    .stream()
                    .collect(Collectors.groupingBy(tran ->
                            getSecurityGroupKey(tran.getIsin(), tran.getCode(), tran.getSeries())))
                    .entrySet()
                    .stream()
                    .map(securityEntry -> getSecurityResponse(
                            securityEntry.getValue(),
                            securityClosePriceMap.getOrDefault(securityEntry.getKey(), BigDecimal.ZERO)
                    ))
                    .toList();
            return SecuritiesResponse.PortfolioWiseResponse.builder()
                    .portfolioId(portfolioId)
                    .holdingSecurityDTO(holdingSecurities)
                    .build();
        }
    }


    private EquityTransaction addIsinCodeSeries(EquityTransaction tran, Set<String> isinList,
                                                Set<String> codeList, Set<String> seriesList) {

        isinList.add(tran.getIsin());
        codeList.add(tran.getCode());
        seriesList.add(tran.getSeries());
        return tran;
    }

    private Map<String, BigDecimal> getSecurityClosePriceMap(Set<String> isinList, Set<String> codeList,
                                                             Set<String> seriesList, LocalDate endDate) {
        Map<String, BigDecimal> securityClosePrice = equityPriceHistoryDao
                .findLatestClosePrice(isinList, codeList, seriesList, endDate)
                .stream()
                .collect(Collectors.toMap(price -> getSecurityGroupKey(price.getIsin(), price.getCode(), price.getSeries()),
                        EquityPriceHistory::getClosePrice, (p1, p2) -> p1));

        log.info("Eq security close price map : {}", securityClosePrice.size());
        return securityClosePrice;
    }

    private SecuritiesResponse.HoldingSecurityDTO getSecurityResponse(List<EquityTransaction> transactions, BigDecimal closePrice) {


        Map<String, List<EquityTransaction>> tranTypeTransactionMap = transactions.stream()
                .collect(Collectors.groupingBy(EquityTransaction::getTransactionType));

        List<EquityTransaction> buyTranSortedByFifo = tranTypeTransactionMap.getOrDefault("Buy", Collections.emptyList())
                .stream()
                .sorted(Comparator.comparing(EquityTransaction::getTransactionDate))
                .toList();

        List<EquityTransaction> sellTrans = tranTypeTransactionMap.getOrDefault("Sell", Collections.emptyList())
                .stream().sorted(Comparator.comparing(EquityTransaction::getTransactionDate)).toList();


        List<EquityTransaction> buyTranSortedByLifo = buyTranSortedByFifo.stream()
                .sorted(Comparator.comparing(EquityTransaction::getTransactionDate).reversed()).toList();

        List<EquityTransaction> buyTranSortedByPrice = buyTranSortedByFifo.stream()
                .sorted(Comparator.comparing(EquityTransaction::getPricePrUnit))
                .toList();

        sellTrans.forEach(sellTran -> {

            if (sellTran.getValuationMethod().equalsIgnoreCase("fifo"))
                updateBuyTransaction(buyTranSortedByFifo, sellTran.getQuantity());

            if (sellTran.getValuationMethod().equalsIgnoreCase("lifo"))
                updateBuyTransaction(buyTranSortedByLifo, sellTran.getQuantity());

            if (sellTran.getValuationMethod().equalsIgnoreCase("disposal"))
                updateBuyTransaction(buyTranSortedByPrice, sellTran.getQuantity());

        });


        List<SecuritiesResponse.TransactionDetail> details = buyTranSortedByFifo.stream()
                .filter(tran -> tran.getQuantity() > 0)
                .map(tran -> getSecurityDetail(tran, closePrice))
                .toList();


        return new SecuritiesResponse.HoldingSecurityDTO(
                transactions.get(0).getIsin(),
                transactions.get(0).getSeries(),
                transactions.get(0).getCode(),
                details
        );
    }

    private SecuritiesResponse.TransactionDetail getSecurityDetail(EquityTransaction tran, BigDecimal closePrice) {
        return SecuritiesResponse.TransactionDetail.builder()

                .costValue(tran.getPricePrUnit().multiply(BigDecimal.valueOf(tran.getQuantity())))
                .UnrealizedGainLoss(closePrice.multiply(BigDecimal.valueOf(tran.getQuantity())))
                /*  .date(tran.getTransactionDate().toString())
                  .quantity(tran.getQuantity())
                  .costValue(tran.getPricePrUnit().multiply(BigDecimal.valueOf(tran.getQuantity())))
                  (closePrice.multiply(BigDecimal.valueOf(tran.getQuantity())))*/
                .build();
    }

    private void updateBuyTransaction(List<EquityTransaction> buyTran, Integer sellQuantity) {

        AtomicReference<Integer> remainingQuantity = new AtomicReference<>(sellQuantity);
        buyTran.stream()
                .filter(tran -> tran.getQuantity() > 0)
                .forEach(tran -> updateQuantity(tran, remainingQuantity));
    }

    private void updateQuantity(EquityTransaction tran, AtomicReference<Integer> remainingQuantity) {

        if (tran.getQuantity() >= remainingQuantity.get()) {
            tran.setQuantity(tran.getQuantity() - remainingQuantity.get());
            remainingQuantity.set(0);
        } else {
            remainingQuantity.set(remainingQuantity.get() - tran.getQuantity());
            tran.setQuantity(0);
        }
    }

    private String getSecurityGroupKey(String isin, String code, String series) {
        return isin.concat("-").concat(code).concat("-").concat(series);
    }

    private Set<String> getOrNull(Set<String> list) {
        return Objects.isNull(list) || list.isEmpty() ? null : list;
    }


}

