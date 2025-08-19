package com.sharemarket.invest.service.abstraction;

import com.sharemarket.invest.dto.request.SecuritiesRequest;
import com.sharemarket.invest.dto.request.SecuritiesResponse;
import com.sharemarket.invest.dto.response.MutualFundUnRealizeGainLossResponse;

public interface MutualFundTransactionWiseUnrealizedGainLoseService {

    MutualFundUnRealizeGainLossResponse getTransactionWiseUnRealizationGainLoss(SecuritiesRequest request);

}
