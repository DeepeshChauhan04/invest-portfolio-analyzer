package com.sharemarket.invest.controller;

import com.sharemarket.invest.dto.request.EquityPriceHistoryRequest;
import com.sharemarket.invest.dto.response.EquityPriceHistoryResponse;
import com.sharemarket.invest.service.abstraction.EquityPriceHistoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/equity_price_history")
public class EquityPriceHistoryController {

    @Autowired
    private EquityPriceHistoryService equityPriceHistoryService;

    @PostMapping("/generate")
    public String generate() {
        equityPriceHistoryService.generateAndInsertHistory();
        return "Price history inserted successfully";
    }

    @GetMapping("/getAll")
    public List<EquityPriceHistoryResponse> getALlEquityPriceHistory() {
        List<EquityPriceHistoryResponse> listOfEquityPriceHistory = equityPriceHistoryService.getALlEquityPriceHistory();
        log.info("ALl List Of Equity Price History {}", listOfEquityPriceHistory.size());
        return listOfEquityPriceHistory;
    }


    @GetMapping("/ByDate")
    public ResponseEntity<List<EquityPriceHistoryResponse>> getByTradeDate(@Valid @RequestBody EquityPriceHistoryRequest equityPriceHistoryRequest) {

        if (equityPriceHistoryRequest.getTradeDate() == null) {

            return ResponseEntity.badRequest().build();
        }
        List<EquityPriceHistoryResponse> result = equityPriceHistoryService.getByTradeDate(equityPriceHistoryRequest.getTradeDate());
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete/{id}")
    public String deletePriceHistoryById(@Valid @PathVariable("id") Long id) {
        equityPriceHistoryService.deleteEquityPriceHistory(id);
        return "deleted price history";
    }
/*
    @GetMapping("/between/{startDate}/{endDate}")
    public List<EquityPriceHistoryResponse> getByTradeDateRange(
            @PathVariable("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @PathVariable("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return equityPriceHistoryService.getByTradeDateRange(startDate, endDate);
    }*/
}
