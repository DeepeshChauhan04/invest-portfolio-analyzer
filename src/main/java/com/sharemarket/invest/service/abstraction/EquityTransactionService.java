package com.sharemarket.invest.service.abstraction;


import com.sharemarket.invest.dto.request.EquityTransactionRequest;
import com.sharemarket.invest.dto.request.HoldingsResult;
import com.sharemarket.invest.dto.request.SecuritiesRequest;
import com.sharemarket.invest.dto.request.SecuritiesResponse;
import com.sharemarket.invest.dto.response.EquitySummaryDTO;
import com.sharemarket.invest.dto.response.EquitySummaryResponse;
import com.sharemarket.invest.dto.response.EquityTransactionResponse;

import java.util.List;

public interface EquityTransactionService {

    List<EquityTransactionResponse> getAllEquityTransactionDetails();

    EquityTransactionResponse saveTransaction(EquityTransactionRequest transaction);

    EquitySummaryResponse getEquitySummary(EquityTransactionRequest equityTransactionRequest);

    EquitySummaryDTO getEquityTransactionByLifo(EquityTransactionRequest equityTransactionRequest);



}
