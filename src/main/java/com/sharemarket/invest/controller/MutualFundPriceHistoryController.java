package com.sharemarket.invest.controller;

import com.sharemarket.invest.dto.request.MutualFundPriceHistoryRequest;
import com.sharemarket.invest.dto.response.MutualFundPriceHistoryResponse;
import com.sharemarket.invest.service.abstraction.MutualFundPriceHistoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/mutual_fund_history")
public class MutualFundPriceHistoryController {


    private final MutualFundPriceHistoryService mutualFundPriceHistoryService;

    @PostMapping("/generate")
    public ResponseEntity<String> generate() {
        mutualFundPriceHistoryService.generateAndInsertHistory();
        return ResponseEntity.ok("Price history inserted successfully");
    }

    @GetMapping("/getAll")
    public List<MutualFundPriceHistoryResponse> getALlEquityPriceHistory() {
        List<MutualFundPriceHistoryResponse> listOfMutualFundHistory = mutualFundPriceHistoryService.getAllListOfMutualFundHistory();
        log.info("ALl List Of Mutual Fund History {}", listOfMutualFundHistory.size());
        return listOfMutualFundHistory;
    }

    @GetMapping("/getByDate")
    public ResponseEntity<List<MutualFundPriceHistoryResponse>> getByNavDate(@Valid  @RequestBody MutualFundPriceHistoryRequest mutualFundPriceHistoryRequest) {

        if (mutualFundPriceHistoryRequest.getNavDate() == null) {

            return ResponseEntity.badRequest().build();
        }
        List<MutualFundPriceHistoryResponse> result = mutualFundPriceHistoryService.getByNaveDate(mutualFundPriceHistoryRequest.getNavDate());
        return ResponseEntity.ok(result);
    }
}
