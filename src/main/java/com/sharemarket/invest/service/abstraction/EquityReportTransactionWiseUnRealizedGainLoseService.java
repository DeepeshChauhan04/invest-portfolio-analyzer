package com.sharemarket.invest.service.abstraction;

import com.sharemarket.invest.dto.request.SecuritiesRequest;
import com.sharemarket.invest.dto.request.SecuritiesResponse;

public interface EquityReportTransactionWiseUnRealizedGainLoseService {

    SecuritiesResponse getHoldSecurities(SecuritiesRequest request);
}
