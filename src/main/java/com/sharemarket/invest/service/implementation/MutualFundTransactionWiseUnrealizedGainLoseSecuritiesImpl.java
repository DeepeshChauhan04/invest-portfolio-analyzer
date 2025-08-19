package com.sharemarket.invest.service.implementation;

import com.sharemarket.invest.dao.MutualFundReportDao;
import com.sharemarket.invest.dto.request.SecuritiesRequest;
import com.sharemarket.invest.dto.response.MutualFundUnRealizeGainLossResponse;
import com.sharemarket.invest.entity.MutualFundPriceHistory;
import com.sharemarket.invest.entity.MutualFundTransaction;
import com.sharemarket.invest.service.abstraction.MutualFundTransactionWiseUnrealizedGainLoseService;
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
public class MutualFundTransactionWiseUnrealizedGainLoseSecuritiesImpl implements MutualFundTransactionWiseUnrealizedGainLoseService {
    private final MutualFundReportDao mutualFundReportDao;

    @Override
    public MutualFundUnRealizeGainLossResponse getTransactionWiseUnRealizationGainLoss(SecuritiesRequest request) {

        List<MutualFundTransaction> response = mutualFundReportDao.findAllRequiredSecurities(request.getStartDate()
                , request.getEndDate(), request.getPortfolioIds(), request.getIsinList(), request.getCodeList());

        Set<String> isinList = new HashSet<>();
        Set<String> codeList = new HashSet<>();

        Map<String, List<MutualFundTransaction>> securityTransaction = mutualFundReportDao.findAllRequiredSecurities(request.getStartDate(),
                        request.getEndDate(), request.getPortfolioIds(), request.getIsinList(), request.getCodeList()).stream()
                .map(fund -> addIsinCodeSeries(fund, isinList, codeList))
                .collect(Collectors.groupingBy(fund -> getSecurityGroupKey(fund.getIsin(), fund.getCode())));


        Map<String, BigDecimal> securityClosePriceMap = getSecurityClosePriceMap(request.getIsinList(),
                request.getCodeList(), request.getEndDate());

        List<MutualFundUnRealizeGainLossResponse.PortfolioWiseResponse> list = response.stream()
                .collect(Collectors.groupingBy(MutualFundTransaction::getPortfolioId))
                .entrySet()
                .stream()
                .map(portfolioList ->
                        gePortfolioWiseList(portfolioList, securityClosePriceMap)).toList();


        log.info("MutualFund holding securities list size : {}", list.size());

        return MutualFundUnRealizeGainLossResponse.builder()
                .details(list)
                .build();
    }

    private MutualFundUnRealizeGainLossResponse.PortfolioWiseResponse gePortfolioWiseList(Map.Entry<Long, List<MutualFundTransaction>> portfolioList, Map<String, BigDecimal> securityClosePriceMap) {

        Long portfolio = portfolioList.getKey();

        List<MutualFundUnRealizeGainLossResponse.HoldingSecurityDTO> record = portfolioList
                .getValue()
                .stream()
                .collect(Collectors.groupingBy(list ->
                        getSecurityGroupKey(list.getIsin(), list.getCode())))
                .entrySet()
                .stream()
                .map(stringListEntry -> getSecurityResponse(
                        stringListEntry.getValue(),
                        securityClosePriceMap.getOrDefault(stringListEntry.getKey(), BigDecimal.ZERO)
                ))
                .toList();

        return MutualFundUnRealizeGainLossResponse.PortfolioWiseResponse.builder()
                .portfolioId(portfolio)
                .holdingSecurityDTO(record)
                .build();
    }

    private MutualFundTransaction addIsinCodeSeries(MutualFundTransaction fund, Set<String> isinList, Set<String> codeList) {

        isinList.add(fund.getIsin());
        codeList.add(fund.getCode());
        return fund;
    }

    private MutualFundUnRealizeGainLossResponse.HoldingSecurityDTO getSecurityResponse(List<MutualFundTransaction> transactions, BigDecimal closePrice) {

        Map<String, List<MutualFundTransaction>> tranTypeTransactionMap = transactions.stream()
                .collect(Collectors.groupingBy(MutualFundTransaction::getTransactionType));

        List<MutualFundTransaction> buyTranSortedByFifo = tranTypeTransactionMap.getOrDefault("Buy", Collections.emptyList())
                .stream()
                .sorted(Comparator.comparing(MutualFundTransaction::getTransactionDate))
                .toList();

        List<MutualFundTransaction> sellTrans = tranTypeTransactionMap.getOrDefault("Sell", Collections.emptyList())
                .stream().sorted(Comparator.comparing(MutualFundTransaction::getTransactionDate)).toList();

        List<MutualFundTransaction> buyTranSortedByLifo = buyTranSortedByFifo.stream()
                .sorted(Comparator.comparing(MutualFundTransaction::getTransactionDate).reversed()).toList();

        List<MutualFundTransaction> buyTranSortedByPrice = buyTranSortedByFifo.stream()
                .sorted(Comparator.comparing(MutualFundTransaction::getNavPrUnite))
                .toList();

        sellTrans.forEach(sellTran -> {

            if (sellTran.getValuationMethod().equalsIgnoreCase("fifo"))
                updateBuyTransaction(buyTranSortedByFifo, sellTran.getUnits());

            if (sellTran.getValuationMethod().equalsIgnoreCase("lifo"))
                updateBuyTransaction(buyTranSortedByLifo, sellTran.getUnits());

            if (sellTran.getValuationMethod().equalsIgnoreCase("disposal"))
                updateBuyTransaction(buyTranSortedByPrice, sellTran.getUnits());

        });

        List<MutualFundUnRealizeGainLossResponse.TransactionDetail> details = buyTranSortedByFifo.stream()
                .filter(tran -> tran.getUnits() > 0)
                .map(tran -> getSecurityDetail(tran, closePrice))
                .toList();

        return MutualFundUnRealizeGainLossResponse.HoldingSecurityDTO
                .builder()
                .isin(transactions.get(0).getIsin())
                .code(transactions.get(0).getCode())
                .detail(details).build();
    }

    private Map<String, BigDecimal> getSecurityClosePriceMap(Set<String> isinList, Set<String> codeList, LocalDate endDate) {
        Map<String, BigDecimal> securityClosePrice = mutualFundReportDao
                .findLatestClosePrice(isinList, codeList, endDate)
                .stream()
                .collect(Collectors.toMap(price -> getSecurityGroupKey(price.getIsin(), price.getCode())
                        , MutualFundPriceHistory::getNav, (p1, p2) -> p1));
        log.info("MutualFund security close price map : {}", securityClosePrice.size());
        return securityClosePrice;
    }

    private MutualFundUnRealizeGainLossResponse.TransactionDetail getSecurityDetail(MutualFundTransaction tran, BigDecimal closePrice) {
        return MutualFundUnRealizeGainLossResponse.TransactionDetail.builder()
                .date(tran.getTransactionDate())
                .quantity(tran.getUnits())
                .costValue(tran.getNavPrUnite().multiply(BigDecimal.valueOf(tran.getUnits())))
                .UnrealizedGainLoss(closePrice.multiply(BigDecimal.valueOf(tran.getUnits())))
                .build();
    }

    private void updateBuyTransaction(List<MutualFundTransaction> buyTran, Integer sellQuantity) {

        AtomicReference<Integer> remainingQuantity = new AtomicReference<>(sellQuantity);
        buyTran.stream()
                .filter(tran -> tran.getUnits() > 0)
                .forEach(tran -> updateQuantity(tran, remainingQuantity));


    }

    private void updateQuantity(MutualFundTransaction tran, AtomicReference<Integer> remainingQuantity) {

        if (tran.getUnits() >= remainingQuantity.get()) {
            tran.setUnits(tran.getUnits() - remainingQuantity.get());
            remainingQuantity.set(0);
        } else {
            remainingQuantity.set(remainingQuantity.get() - tran.getUnits());
            tran.setUnits(0);
        }


    }


    private String getSecurityGroupKey(String isin, String code) {
        return isin.concat("-").concat(code).concat("-");
    }

    private LocalDate toLocalDate(String date) {
        return LocalDate.parse(date);
    }
}
