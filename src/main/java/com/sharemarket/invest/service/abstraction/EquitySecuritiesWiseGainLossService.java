package com.sharemarket.invest.service.abstraction;

import com.sharemarket.invest.dto.request.SecuritiesRequest;
import com.sharemarket.invest.dto.response.SecuritiesRealizedGainLossResponse;
import com.sharemarket.invest.dto.response.SecuritiesUnRealizedGainLossResponse;

public interface EquitySecuritiesWiseGainLossService {


    SecuritiesRealizedGainLossResponse getSecuritiesWiseRealizedGainLoss(SecuritiesRequest request);

    SecuritiesUnRealizedGainLossResponse getUnrealizedGainLoss(SecuritiesRequest request);

}

