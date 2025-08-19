package com.sharemarket.invest.service.implementation;

import com.sharemarket.invest.dao.AccountsDao;
import com.sharemarket.invest.dao.UserDao;
import com.sharemarket.invest.dto.request.AccountRequest;
import com.sharemarket.invest.dto.response.AccountResponse;
import com.sharemarket.invest.entity.AccountDetails;
import com.sharemarket.invest.exception.CustomInvestException;
import com.sharemarket.invest.service.abstraction.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountsDao accountsDao;
    private final ModelMapper modelMapper;
    private final UserDao userDao;

    @Override
    public AccountResponse creatAccountsDetails(AccountRequest accountRequest) {

        if (!userDao.existsByUserIdAndStatus(accountRequest.getUserId(), 1)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with ID: " + accountRequest.getUserId());
        }
        boolean hasDefault = accountsDao
                .findByUserIdAndDefaultAccountTrue(accountRequest.getUserId())
                .isPresent();
        AccountDetails accountSave = modelMapper.map(accountRequest, AccountDetails.class);
        accountSave.setCreatedAt(LocalDateTime.now());
        accountSave.setStatus(1);
        accountSave.setDefaultAccount(!hasDefault);
        accountSave.setUpdatedAt(LocalDateTime.now());
        AccountDetails saveAccountDetails = accountsDao.save(accountSave);
        AccountResponse responseAccount = modelMapper.map(saveAccountDetails, AccountResponse.class);
        log.info(" {}", responseAccount.getAccountId());
        return responseAccount;
    }

    @Override
    public List<AccountResponse> getAllAccount() {
        List<AccountDetails> allAccounts = accountsDao.findAllByStatus(1);
        return allAccounts.stream()
                .map(account -> modelMapper
                        .map(account, AccountResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public AccountResponse getByAccountId(Long id) {

        AccountDetails accountDetails = accountsDao.findByAccountIdAndStatus(id, 1);
        if (accountDetails == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with ID");
        }

        return modelMapper.map(accountDetails, AccountResponse.class);
    }

    @Override
    public void deleteAccountById(Long id) {

        boolean isValidAccount = accountsDao.existsByAccountIdAndStatus(id, 1);
        if (!isValidAccount) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid or inactive portfolio ID.");
        }
        AccountDetails account = accountsDao.findByAccountIdAndStatus(id, 1);
        account.setStatus(2);
        accountsDao.save(account);
    }

    @Override
    public List<AccountResponse> getAccountByUserId(Long userId) {
        List<AccountDetails> accounts = accountsDao.findAccountDetailsByUserIdAndStatus(userId, 1);
        return accounts.stream().map(accountDetails -> modelMapper.map(accountDetails, AccountResponse.class)).collect(Collectors.toList());
    }

    @Override
    public String updateDefaultAccountByUserId(AccountRequest accountRequest) {
        List<AccountDetails> accounts = accountsDao.findAccountDetailsByUserIdAndStatus(accountRequest.getUserId(), 1);
        if (accounts.isEmpty()) {
            throw new CustomInvestException(0, "Invalid or inactive userId", null);
        }
        for (AccountDetails ac : accounts) {
            if (ac.getDefaultAccount() == true) {
                ac.setDefaultAccount(false);
                accountsDao.save(ac);
            }
        }
        AccountDetails accountList = accountsDao.findByAccountIdAndStatus(accountRequest.getAccountId(), 1);

        accountList.setDefaultAccount(true);
        accountsDao.save(accountList);

        return "success";
    }
}

