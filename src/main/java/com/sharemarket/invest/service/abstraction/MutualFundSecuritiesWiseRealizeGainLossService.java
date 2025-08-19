package com.sharemarket.invest.service.abstraction;

import com.sharemarket.invest.dto.request.SecuritiesRequest;
import com.sharemarket.invest.dto.response.MutualFundSecuritiesRealizedGainLossResponse;
import com.sharemarket.invest.dto.response.MutualFundSecuritiesUnRealizedGainLossResponse;

public interface MutualFundSecuritiesWiseRealizeGainLossService {

    MutualFundSecuritiesRealizedGainLossResponse getSecuritiesWiseRealizedGainLoss(SecuritiesRequest request );

    MutualFundSecuritiesUnRealizedGainLossResponse getSecuritiesWiseUnRealizedGainLoss(SecuritiesRequest request);

}
