package com.sharemarket.invest.controller;

import com.sharemarket.invest.dto.request.AccountRequest;
import com.sharemarket.invest.dto.response.AccountResponse;
import com.sharemarket.invest.service.abstraction.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
public class AccountsController {
    private final AccountService accountService;


    @PostMapping("/create")
    public ResponseEntity<?> creatAccountDetails(@Valid @RequestBody AccountRequest accountRequest) {
        try {
            AccountResponse account = accountService.creatAccountsDetails(accountRequest);
            return ResponseEntity.ok(account);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getAllAccounts")
    public ResponseEntity<List<AccountResponse>> getAccounts() {

        List<AccountResponse> accountsList = accountService.getAllAccount();
        log.info("AccountsController {} ", accountsList.size());
        return ResponseEntity.ok(accountsList);
    }

    @GetMapping("/getByAccountId/{id}")
    public ResponseEntity<AccountResponse> getByAccountListId(@PathVariable Long id) {

        AccountResponse listOfAccount = accountService.getByAccountId(id);
        return ResponseEntity.ok(listOfAccount);
    }

    @GetMapping("/byUserId/{id}")
    public List<AccountResponse> getAccountByUserId(@PathVariable("id") Long id) {
        return accountService.getAccountByUserId(id);
    }

    @DeleteMapping("/deleteById/{id}")
    public String deleteAccountById(@PathVariable("id") long id) {
        accountService.deleteAccountById(id);
        return "Delete Account ";

    }

    @PutMapping("/update")
    public String updateDefaultAccount(@RequestBody AccountRequest accountRequest) {

        return accountService.updateDefaultAccountByUserId(accountRequest);
    }
}
