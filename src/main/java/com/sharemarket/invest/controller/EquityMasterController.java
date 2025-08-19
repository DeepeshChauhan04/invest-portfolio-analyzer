package com.sharemarket.invest.controller;

import com.sharemarket.invest.dto.request.EquityMasterRequest;
import com.sharemarket.invest.dto.response.EquityMasterResponse;
import com.sharemarket.invest.service.abstraction.EquityMasterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/equity")
public class EquityMasterController {
    private final EquityMasterService equityMasterService;


    @PostMapping("/generate")
    public ResponseEntity<String> generate() {
        equityMasterService.generateEquityMaster();
        return ResponseEntity.ok(" mutual fund  inserted successfully");
    }


    @GetMapping("/getALlEquityDetails")
    public List<EquityMasterResponse> getAllEquities() {
        return equityMasterService.getAllEquities();
    }

    @PostMapping("/getFilterEquity")
    public ResponseEntity<List<EquityMasterResponse>> filterEquities(@Valid @RequestBody EquityMasterRequest request) {

        List<EquityMasterResponse> allFilteredEquityList = equityMasterService.getFilterEquitiesList(request);
        return ResponseEntity.ok(allFilteredEquityList);
    }

    @GetMapping("/getByEqId/{id}")
    public EquityMasterResponse getByEqIdToGetEquityMaster(@Valid @PathVariable("id") Long id) {
        return equityMasterService.getByEqId(id);
    }
}

