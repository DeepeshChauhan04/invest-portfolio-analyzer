package com.sharemarket.invest.controller;

import com.sharemarket.invest.dto.request.MutualFundTransactionRequest;
import com.sharemarket.invest.dto.response.MutualFundTransactionResponse;
import com.sharemarket.invest.service.abstraction.MutualFundTransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/mutualFund")
@RequiredArgsConstructor
public class MutualFundTransactionController {
    private final MutualFundTransactionService mutualFundTransactionService;

    @PostMapping("/transaction")
    public MutualFundTransactionResponse createMutualFundTransaction(@Valid @RequestBody MutualFundTransactionRequest mutualFundTransactionRequest) {

        MutualFundTransactionResponse mutualFundTransactionResponse = mutualFundTransactionService.saveTransaction(mutualFundTransactionRequest);
        log.info("Created Mutual Fund Transaction {}", mutualFundTransactionResponse.getStatus());
        return mutualFundTransactionResponse;
    }
    @GetMapping("/getAllEquityTransaction")
    public ResponseEntity<List<MutualFundTransactionResponse>> getALlEquityTransaction() {

        List<MutualFundTransactionResponse> listOfEquity = mutualFundTransactionService.getAllMutualFundTransactionDetails();
        return ResponseEntity.ok(listOfEquity);

    }



}