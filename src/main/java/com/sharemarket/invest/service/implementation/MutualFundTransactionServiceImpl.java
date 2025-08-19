package com.sharemarket.invest.service.implementation;

import com.sharemarket.invest.dao.AccountsDao;
import com.sharemarket.invest.dao.MutualFundTransactionDao;
import com.sharemarket.invest.dao.PortfolioDetailsDao;
import com.sharemarket.invest.dto.request.MutualFundTransactionRequest;
import com.sharemarket.invest.dto.response.MutualFundTransactionResponse;
import com.sharemarket.invest.entity.AccountDetails;
import com.sharemarket.invest.entity.MutualFundTransaction;
import com.sharemarket.invest.exception.CustomInvestException;
import com.sharemarket.invest.service.abstraction.MutualFundTransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MutualFundTransactionServiceImpl implements MutualFundTransactionService {
    private final MutualFundTransactionDao mutualFundTransactionDao;
    private final AccountsDao accountsDao;
    private final PortfolioDetailsDao portfolioDetailsDao;
    private final ModelMapper modelMapper;

    @Override
    public List<MutualFundTransactionResponse> getAllMutualFundTransactionDetails() {
        List<MutualFundTransaction> listOfMutualFundTransactions = mutualFundTransactionDao.findAll();
        List<MutualFundTransactionResponse> responseMutualFundTransaction = listOfMutualFundTransactions
                .stream()
                .map(mutualFundTransaction -> modelMapper
                        .map(mutualFundTransaction, MutualFundTransactionResponse.class))
                .collect(Collectors.toList());
        log.info(" {} : ", responseMutualFundTransaction.size());
        return responseMutualFundTransaction;
    }

    @Override
    public MutualFundTransactionResponse saveTransaction(MutualFundTransactionRequest mutualFundTransactionRequest) {
        validateTransaction(mutualFundTransactionRequest);

        AccountDetails account = accountsDao
                .findDefaultAccountByPortfolioId(mutualFundTransactionRequest.getPortfolioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No default account for portfolioId: "
                        + mutualFundTransactionRequest.getPortfolioId()));

        BigDecimal totalPrice = mutualFundTransactionRequest.getNavPrUnite().multiply(BigDecimal.valueOf(mutualFundTransactionRequest.getUnits()));

        if ("BUY".equalsIgnoreCase(mutualFundTransactionRequest.getTransactionType())) {
            if (account.getAccountBalance().compareTo(totalPrice) < 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient account balance");
            }
            account.setAccountBalance(account.getAccountBalance().subtract(totalPrice));
        } else if ("SELL".equalsIgnoreCase(mutualFundTransactionRequest.getTransactionType())) {
            int quantity = mutualFundTransactionDao.getNetAvailableQuantity(mutualFundTransactionRequest.getCode(), mutualFundTransactionRequest.getIsin(), mutualFundTransactionRequest.getPortfolioId());
            int quantityToSell = mutualFundTransactionRequest.getUnits();
            if (quantityToSell > quantity) {
                throw new CustomInvestException(0, "not enough quantity", null);
            }
            account.setAccountBalance(account.getAccountBalance().add(totalPrice));
        }

        account.setUpdatedAt(LocalDateTime.now());
        accountsDao.save(account);

        MutualFundTransaction mutualFundTransaction = modelMapper.map(mutualFundTransactionRequest, MutualFundTransaction.class);
        mutualFundTransaction.setTotalNav(totalPrice);
        mutualFundTransaction.setTransactionDate(LocalDate.now());
        mutualFundTransaction.setCreatedAt(LocalDateTime.now());
        mutualFundTransaction.setStatus(1);
        MutualFundTransaction mutualFundTransactionData = mutualFundTransactionDao.save(mutualFundTransaction);
        MutualFundTransactionResponse mutualFundTransactionResponse = modelMapper
                .map(mutualFundTransactionData, MutualFundTransactionResponse.class);
        log.info(" Mutual Fund Transaction Created {}", mutualFundTransactionResponse.getStatus());
        return mutualFundTransactionResponse;
    }


    private void validateTransaction(MutualFundTransactionRequest mutualFundTransactionRequest) {
        boolean isValidPortfolio = portfolioDetailsDao.existsByPortfolioIdAndStatus(mutualFundTransactionRequest.getPortfolioId(), 1);
        if (!isValidPortfolio) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid or inactive portfolio ID.");
        }
    }
}