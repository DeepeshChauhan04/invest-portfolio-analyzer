package com.sharemarket.invest.controller;


import com.sharemarket.invest.dto.request.EquityTransactionRequest;
import com.sharemarket.invest.dto.response.EquitySummaryDTO;
import com.sharemarket.invest.dto.response.EquitySummaryResponse;
import com.sharemarket.invest.dto.response.EquityTransactionResponse;
import com.sharemarket.invest.service.abstraction.EquityTransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/equityTransaction")
@RequiredArgsConstructor
public class EquityTransactionController {

    private final EquityTransactionService equityTransactionService;

    @GetMapping("/getAllEquityTransaction")
    public ResponseEntity<List<EquityTransactionResponse>> getALlEquityTransaction() {

        List<EquityTransactionResponse> listOfEquity = equityTransactionService.getAllEquityTransactionDetails();
        return ResponseEntity.ok(listOfEquity);

    }


    @PostMapping("/transaction")
    public EquityTransactionResponse createTransaction(@Valid @RequestBody EquityTransactionRequest equityTransactionRequest) {

        EquityTransactionResponse equityTransaction = equityTransactionService.saveTransaction(equityTransactionRequest);
        log.info("created equity transaction {}", equityTransaction.getTransactionType());
        return equityTransaction;
    }

    @GetMapping("/summary")
    public EquitySummaryResponse getEquitySummary(@Valid @RequestBody EquityTransactionRequest equityTransactionRequest) {
        return equityTransactionService.getEquitySummary(equityTransactionRequest);
    }

    @GetMapping("/ByLifo")
    public EquitySummaryDTO getEquityByLifo(@Valid @RequestBody EquityTransactionRequest equityTransactionRequest) {
        return equityTransactionService.getEquityTransactionByLifo(equityTransactionRequest);
    }
}