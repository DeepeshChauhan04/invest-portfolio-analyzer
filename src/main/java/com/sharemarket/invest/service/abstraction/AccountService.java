package com.sharemarket.invest.service.abstraction;

import com.sharemarket.invest.dto.request.AccountRequest;
import com.sharemarket.invest.dto.response.AccountResponse;

import java.util.List;

public interface AccountService {

    AccountResponse creatAccountsDetails(AccountRequest accountRequest);

    List<AccountResponse> getAllAccount();

    AccountResponse getByAccountId(Long id);

    void deleteAccountById(Long id);

    List<AccountResponse> getAccountByUserId(Long userId);

          String updateDefaultAccountByUserId(AccountRequest accountRequest);
}
