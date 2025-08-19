package com.sharemarket.invest.service.abstraction;

import com.sharemarket.invest.dto.request.PortfolioHoldingsResponse;
import com.sharemarket.invest.dto.request.SecuritiesRequest;

public interface EquityReportTransactionWiseRealizedGainLoseService {

     PortfolioHoldingsResponse getRealizedGains(SecuritiesRequest request);

    }
