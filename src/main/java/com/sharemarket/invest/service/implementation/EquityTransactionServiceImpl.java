package com.sharemarket.invest.service.implementation;

import com.sharemarket.invest.dao.AccountsDao;
import com.sharemarket.invest.dao.EquityPriceHistoryDao;
import com.sharemarket.invest.dao.EquityTransactionDao;
import com.sharemarket.invest.dao.PortfolioDetailsDao;
import com.sharemarket.invest.dto.request.EquityTransactionRequest;
import com.sharemarket.invest.dto.response.EquitySummaryDTO;
import com.sharemarket.invest.dto.response.EquitySummaryResponse;
import com.sharemarket.invest.dto.response.EquitySummaryView;
import com.sharemarket.invest.dto.response.EquityTransactionResponse;
import com.sharemarket.invest.entity.AccountDetails;
import com.sharemarket.invest.entity.EquityTransaction;
import com.sharemarket.invest.exception.CustomInvestException;
import com.sharemarket.invest.service.abstraction.EquityTransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EquityTransactionServiceImpl implements EquityTransactionService {

    private final EquityTransactionDao equityTransactionDao;
    private final AccountsDao accountsDao;
    private final PortfolioDetailsDao portfolioDetailsDao;
    private final ModelMapper modelMapper;
    private final EquityPriceHistoryDao equityPriceHistoryDao;

    @Override
    public List<EquityTransactionResponse> getAllEquityTransactionDetails() {
        List<EquityTransaction> listOfEquityTransactions = equityTransactionDao.findAllByStatus(1);
        return listOfEquityTransactions.stream()
                .map(e -> modelMapper.map(e, EquityTransactionResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public EquityTransactionResponse saveTransaction(EquityTransactionRequest request) {
        validateTransaction(request);

        AccountDetails account = accountsDao.findDefaultAccountByPortfolioId(request.getPortfolioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No default account found"));
        BigDecimal totalPrice = request.getPricePrUnit().multiply(BigDecimal.valueOf(request.getQuantity()));

        if ("BUY".equalsIgnoreCase(request.getTransactionType())) {
            if (account.getAccountBalance().compareTo(totalPrice) < 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient account balance");
            }
            account.setAccountBalance(account.getAccountBalance().subtract(totalPrice));

        } else if ("SELL".equalsIgnoreCase(request.getTransactionType())) {
            Integer quantity = equityTransactionDao.getNetAvailableQuantity(request.getCode(), request.getSeries(), request.getIsin(), request.getPortfolioId());
            int quantityToSell = request.getQuantity();
            if (quantityToSell > quantity) {
                throw new CustomInvestException(0, "not enough security", null);
            }
            account.setAccountBalance(account.getAccountBalance().add(totalPrice));
        }

        account.setUpdatedAt(LocalDateTime.now());
        accountsDao.save(account);

        EquityTransaction transaction = modelMapper.map(request, EquityTransaction.class);
        transaction.setTotalPrice(totalPrice);
        transaction.setTransactionDate(LocalDate.now());
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setStatus(1);

        EquityTransaction savedTransaction = equityTransactionDao.save(transaction);
        return modelMapper.map(savedTransaction, EquityTransactionResponse.class);
    }

    @Override
    public EquitySummaryResponse getEquitySummary(EquityTransactionRequest request) {
        EquitySummaryView summary = equityTransactionDao.getEquitySummary(request.getCode(), request.getSeries(), request.getIsin(), request.getPortfolioId());
        log.info("Total buy quantity {}", summary.getTotalBuyQty());
        Integer totalBuyQty = summary.getTotalBuyQty();
        Integer totalSellQty = summary.getTotalSellQty();
        BigDecimal totalBuyValue = summary.getTotalBuyValue();

        int netQty = totalBuyQty - totalSellQty;
        BigDecimal avgBuyPrice = totalBuyQty == 0 ? BigDecimal.ZERO :
                totalBuyValue.divide(BigDecimal.valueOf(totalBuyQty), 2, RoundingMode.HALF_UP);
        BigDecimal sellValue = avgBuyPrice.multiply(BigDecimal.valueOf(totalSellQty));
        BigDecimal profitLoss = sellValue.subtract(totalBuyValue);

        String status = profitLoss.compareTo(BigDecimal.ZERO) >= 0 ? "PROFIT" : "LOSS";

        return new EquitySummaryResponse(
                summary.getTotalBuyQty(),
                summary.getTotalBuyValue(),
                summary.getTotalSellQty(),
                netQty,
                profitLoss,
                status,
                request.getCode(),
                request.getIsin(),
                request.getSeries()
        );
    }


    @Override
    public EquitySummaryDTO getEquityTransactionByLifo(EquityTransactionRequest request) {
        return null;
    }
     /*   List<EquityTransaction> transactions = equityTransactionDao
                .findLifoTransactions(request.getCode(), request.getSeries(), request.getIsin(), request.getPortfolioId());

        Deque<EquityTransaction> lifoStack = new ArrayDeque<>();

        int totalBuyQty = 0;
        int totalSellQty = 0;
        BigDecimal totalBuyValue = BigDecimal.ZERO;
        BigDecimal realizedPL = BigDecimal.ZERO;

        for (EquityTransaction tx : transactions) {
            if ("BUY".equalsIgnoreCase(tx.getTransactionType())) {
                EquityTransaction copy = getCopy(tx);

                lifoStack.push(copy);
                totalBuyQty += tx.getQuantity();
                totalBuyValue = totalBuyValue.add(tx.getTotalPrice());

            } else if ("SELL".equalsIgnoreCase(tx.getTransactionType())
                    && "LIFO".equalsIgnoreCase(tx.getValuationMethod())) {

                int qtyToSell = tx.getQuantity();
                totalSellQty += qtyToSell;

                while (qtyToSell > 0 && !lifoStack.isEmpty()) {
                    EquityTransaction lastBuy = lifoStack.peek();
                    int availableQty = lastBuy.getQuantity();
                    int matchedQty = Math.min(qtyToSell, availableQty);

                    BigDecimal buyPrice = lastBuy.getPricePrUnit();
                    BigDecimal sellPrice = tx.getPricePrUnit();

                    BigDecimal profit = sellPrice.subtract(buyPrice)
                            .multiply(BigDecimal.valueOf(matchedQty));

                    realizedPL = realizedPL.add(profit);

                    qtyToSell -= matchedQty;
                    lastBuy.setQuantity(availableQty - matchedQty);
                    if (lastBuy.getQuantity() == 0) {
                        lifoStack.pop();
                    }
                }
            }
        }
        BigDecimal unrealized = BigDecimal.ZERO;
        for (EquityTransaction buy : lifoStack) {
            BigDecimal value = buy.getPricePrUnit()
                    .multiply(BigDecimal.valueOf(buy.getQuantity()));
            unrealized = unrealized.add(value);
        }
        String status = realizedPL.compareTo(BigDecimal.ZERO) >= 0 ? "PROFIT" : "LOSS";
        EquitySummaryDTO eq = new EquitySummaryDTO();
        eq.setCode(request.getCode());
        eq.setIsin(request.getIsin());
        eq.setSeries(request.getSeries());
        eq.setStatus(status);
        eq.setTotalBuyQty(totalBuyQty);
        eq.setTotalBuyValue(totalBuyValue);
        eq.setTotalSellQty(totalSellQty);
        eq.setUnrealized(unrealized);
        eq.setRealizedPL(realizedPL);
        return eq;
    }
    private static EquityTransaction getCopy(EquityTransaction et) {
        EquityTransaction copy = new EquityTransaction();
        copy.setCode(et.getCode());
        copy.setSeries(et.getSeries());
        copy.setIsin(et.getIsin());
        copy.setPortfolioId(et.getPortfolioId());
        copy.setPricePrUnit(et.getPricePrUnit());
        copy.setQuantity(et.getQuantity());
        copy.setTransactionType(et.getTransactionType());
        copy.setTotalPrice(et.getTotalPrice());
        copy.setTransactionDate(et.getTransactionDate());
        copy.setValuationMethod(et.getValuationMethod());
        return copy;
    }*/


    private void validateTransaction(EquityTransactionRequest request) {
        boolean validPortfolio = portfolioDetailsDao.existsByPortfolioIdAndStatus(request.getPortfolioId(), 1);
        if (!validPortfolio) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid or inactive portfolio ID.");
        }
    }
    private List<String> makeNullIfEmpty(List<String> list) {
        return list.isEmpty() ? null : list;
    }

}
