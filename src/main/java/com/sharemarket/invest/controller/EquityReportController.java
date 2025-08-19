package com.sharemarket.invest.controller;


import com.sharemarket.invest.dto.request.PortfolioHoldingsResponse;
import com.sharemarket.invest.dto.request.SecuritiesRequest;
import com.sharemarket.invest.dto.request.SecuritiesResponse;
import com.sharemarket.invest.dto.response.SecuritiesRealizedGainLossResponse;
import com.sharemarket.invest.dto.response.SecuritiesUnRealizedGainLossResponse;
import com.sharemarket.invest.service.abstraction.EquityReportTransactionWiseRealizedGainLoseService;
import com.sharemarket.invest.service.abstraction.EquityReportTransactionWiseUnRealizedGainLoseService;
import com.sharemarket.invest.service.abstraction.EquitySecuritiesWiseGainLossService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/equity")
public class EquityReportController {

    private final EquityReportTransactionWiseUnRealizedGainLoseService equityReportTransactionWiseUnRealizedGainLoseService;
    private final EquityReportTransactionWiseRealizedGainLoseService equityReportTransactionWiseRealizedGainLoseService;
    private final EquitySecuritiesWiseGainLossService sr;

    @PostMapping("/transactionWise/UnRealizedGainLose")
    public SecuritiesResponse getHoldET(@RequestBody SecuritiesRequest request) {
        return equityReportTransactionWiseUnRealizedGainLoseService.getHoldSecurities(request);

    }

    @PostMapping("/transactionWise/realizedGainLose")
    public PortfolioHoldingsResponse getRealizedGain(@RequestBody SecuritiesRequest request) {

        return equityReportTransactionWiseRealizedGainLoseService.getRealizedGains(request);
    }

    @PostMapping("/securities/realizedGainLose")
    public SecuritiesRealizedGainLossResponse getRealizedGainBySecurities(@RequestBody SecuritiesRequest request) {
        return sr.getSecuritiesWiseRealizedGainLoss(request);
    }


    @PostMapping("/securities/unrealizedGainLoss")
    public SecuritiesUnRealizedGainLossResponse getUnrealizedGainLossBySecurities(@RequestBody SecuritiesRequest request) {
        return sr.getUnrealizedGainLoss(request);
    }


}