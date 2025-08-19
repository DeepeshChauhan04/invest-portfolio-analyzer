package com.sharemarket.invest.service.abstraction;

import com.sharemarket.invest.dto.request.SecuritiesRequest;
import com.sharemarket.invest.dto.response.MutualFundRealizeGainLossResponse;

public interface MutualFundRealizationGainLossService {

    MutualFundRealizeGainLossResponse getTransactionWiseRealizationGainLoss(SecuritiesRequest request);

}
