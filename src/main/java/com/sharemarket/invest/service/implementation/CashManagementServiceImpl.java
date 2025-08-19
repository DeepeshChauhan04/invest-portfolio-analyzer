package com.sharemarket.invest.service.implementation;

import com.sharemarket.invest.dao.AccountsDao;
import com.sharemarket.invest.dao.CashManagementDao;
import com.sharemarket.invest.dao.PortfolioDetailsDao;
import com.sharemarket.invest.dto.request.CashManagementRequest;
import com.sharemarket.invest.dto.response.CashManagementResponse;
import com.sharemarket.invest.entity.AccountDetails;
import com.sharemarket.invest.entity.CashManagement;
import com.sharemarket.invest.service.abstraction.CashManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CashManagementServiceImpl implements CashManagementService {

    private final CashManagementDao cashManagementDao;
    private final PortfolioDetailsDao portfolioDetailsDao;
    private final AccountsDao accountsDao;
    private final ModelMapper modelMapper;

    @Override

    public CashManagementResponse saveCashTransaction(CashManagementRequest cashManagementRequest) {
        if (!portfolioDetailsDao.existsByPortfolioIdAndStatus(cashManagementRequest.getPortfolioId(), 1)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "portfolio id  not found");

        }
        AccountDetails accounts = accountsDao.findByAccountIdAndStatus(cashManagementRequest.getAccountNumber(), 1);

        if (Objects.isNull(accounts))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");

        log.info("mismanagement {} {}", accounts.getAccountBalance(), cashManagementRequest.getAmount());

        if (cashManagementRequest.getTransactionType().equalsIgnoreCase("WITHDRAWAL")
                && accounts.getAccountBalance().compareTo(cashManagementRequest.getAmount()) < 0) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient account balance");
        }

        if (cashManagementRequest.getTransactionType().equalsIgnoreCase("WITHDRAWAL"))
            accounts.setAccountBalance(accounts.getAccountBalance().subtract(cashManagementRequest.getAmount()));
        else
            accounts.setAccountBalance(accounts.getAccountBalance().add(cashManagementRequest.getAmount()));
        accounts.setUpdatedAt(LocalDateTime.now());

        CashManagement saveCash = modelMapper.map(cashManagementRequest, CashManagement.class);
        saveCash.setStatus(1);
        saveCash.setTransactionDate(LocalDate.now());
        saveCash.setUpdatedAt(LocalDateTime.now());
        saveCash.setCreatedAt(LocalDateTime.now());

        CashManagement responseCash = cashManagementDao.save(saveCash);
        accountsDao.save(accounts);

        return modelMapper.map(responseCash, CashManagementResponse.class);
    }

    @Override
    public List<CashManagementResponse> fetchAllCashTransaction() {
        List<CashManagement> listOfCashManagement = cashManagementDao.findAll();
        return listOfCashManagement.stream().map(cash -> modelMapper
                        .map(cash, CashManagementResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CashManagementResponse> fetchCashTransactionByPortfolioId(Long id) {

        List<CashManagement> cashManagementList = cashManagementDao.findByPortfolioId(id);
        if (cashManagementList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Portfolio not found");
        }
        //  CashManagementResponse response = modelMapper.map(cashManagementList, CashManagementResponse.class);


        return cashManagementList.stream().map(cashManagement -> modelMapper.map(cashManagement, CashManagementResponse.class)).toList();

    }
}
