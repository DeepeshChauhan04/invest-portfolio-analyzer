package com.sharemarket.invest.controller;

import com.sharemarket.invest.dto.request.MutualFundMasterRequest;
import com.sharemarket.invest.dto.response.MutualFundMasterResponse;
import com.sharemarket.invest.service.abstraction.MutualFundMasterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mutualFunds")
@RequiredArgsConstructor
public class MutualFundMasterController {

    private final MutualFundMasterService mutualFundMasterService;



    @PostMapping("/generate")
    public ResponseEntity<String> generate() {
        mutualFundMasterService.generateMutualFundMaster();
        return ResponseEntity.ok(" mutual fund  inserted successfully");
    }

    @GetMapping("/getAllMutualFunds")
    public ResponseEntity<List<MutualFundMasterResponse>> getALlMutualFundDetails() {

        List<MutualFundMasterResponse> listOfMutualFundamentals = mutualFundMasterService.getAllMutualFunds();
        return ResponseEntity.ok(listOfMutualFundamentals);


    }

    @PostMapping("/getFiltersMutualFunds")
    public ResponseEntity<List<MutualFundMasterResponse>> getFiltersMutualFundsList(@Valid @RequestBody MutualFundMasterRequest
                                                                                            mutualFundMasterRequest) {
        List<MutualFundMasterResponse> listOfMutualFunds = mutualFundMasterService.getFilteredMutualFunds
                (mutualFundMasterRequest);
        return ResponseEntity.ok(listOfMutualFunds);
    }

}
