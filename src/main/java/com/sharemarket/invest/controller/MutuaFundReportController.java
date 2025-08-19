package com.sharemarket.invest.controller;

import com.sharemarket.invest.dto.request.SecuritiesRequest;
import com.sharemarket.invest.dto.response.MutualFundRealizeGainLossResponse;
import com.sharemarket.invest.dto.response.MutualFundSecuritiesRealizedGainLossResponse;
import com.sharemarket.invest.dto.response.MutualFundSecuritiesUnRealizedGainLossResponse;
import com.sharemarket.invest.dto.response.MutualFundUnRealizeGainLossResponse;
import com.sharemarket.invest.service.abstraction.MutualFundRealizationGainLossService;
import com.sharemarket.invest.service.abstraction.MutualFundSecuritiesWiseRealizeGainLossService;
import com.sharemarket.invest.service.abstraction.MutualFundTransactionWiseUnrealizedGainLoseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mutualFund")
public class MutuaFundReportController {


    private final MutualFundSecuritiesWiseRealizeGainLossService securitiesWiseRealizeGainLossService;
    private final MutualFundRealizationGainLossService mutualFundRealizationGainLossService;
    private final MutualFundTransactionWiseUnrealizedGainLoseService mutualFundTransactionWiseUnrealizedGainLoseService;

    @PostMapping("/transactionWise/urealizedGainLose")
    public MutualFundUnRealizeGainLossResponse getTransactionWiseUnrealizedGainLose(@RequestBody SecuritiesRequest request) {
        return mutualFundTransactionWiseUnrealizedGainLoseService.getTransactionWiseUnRealizationGainLoss(request);

    }

    @PostMapping("/transactionWise/realizedGainLose")
    public MutualFundRealizeGainLossResponse getTransactionWiseRealizedGainLose(@RequestBody SecuritiesRequest request) {
        return mutualFundRealizationGainLossService.getTransactionWiseRealizationGainLoss(request);

    }

    @PostMapping("/securities/realizedGainLose")
    public MutualFundSecuritiesRealizedGainLossResponse getRealizedGainBySecurities(@RequestBody SecuritiesRequest request) {
        return securitiesWiseRealizeGainLossService.getSecuritiesWiseRealizedGainLoss(request);
    }

    @PostMapping("/securities/unrealizedGainLoss")
    public MutualFundSecuritiesUnRealizedGainLossResponse getUnrealizedGainLossBySecurities(@RequestBody SecuritiesRequest request) {
        return securitiesWiseRealizeGainLossService.getSecuritiesWiseUnRealizedGainLoss(request);
    }


}
