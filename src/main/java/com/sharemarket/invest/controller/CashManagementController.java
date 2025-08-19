package com.sharemarket.invest.controller;

import com.sharemarket.invest.dto.request.CashManagementRequest;
import com.sharemarket.invest.dto.response.CashManagementResponse;
import com.sharemarket.invest.service.abstraction.CashManagementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cashManagement")
public class CashManagementController {

    private final CashManagementService cashManagementService;

    @PostMapping("/addTransaction")
    public ResponseEntity<CashManagementResponse> createCashTransaction(@Valid @RequestBody CashManagementRequest cashManagementRequest) {

        CashManagementResponse saveCashTransactions = cashManagementService.saveCashTransaction(cashManagementRequest);
        return ResponseEntity.ok(saveCashTransactions);
    }

    @GetMapping("/getALlTransactionList")
    public ResponseEntity<List<CashManagementResponse>> getALlCashTransaction() {

        List<CashManagementResponse> allCashTransaction = cashManagementService.fetchAllCashTransaction();
        return ResponseEntity.ok(allCashTransaction);
    }

    @GetMapping("/getALlTransactionByPortfolioId/{id}")
    public ResponseEntity<List<CashManagementResponse>> getByCashTransaction(@PathVariable Long id) {
        List<CashManagementResponse> cashTransactionPortfolioId = cashManagementService.fetchCashTransactionByPortfolioId(id);
        return ResponseEntity.ok(cashTransactionPortfolioId);

    }

}
