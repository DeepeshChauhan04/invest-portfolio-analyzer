package com.sharemarket.invest.service.abstraction;

import com.sharemarket.invest.dto.request.MutualFundMasterRequest;
import com.sharemarket.invest.dto.response.MutualFundMasterResponse;

import java.util.List;

public interface MutualFundMasterService {


    void generateMutualFundMaster();

    List<MutualFundMasterResponse> getAllMutualFunds();

    List<MutualFundMasterResponse> getFilteredMutualFunds(MutualFundMasterRequest mutualFundMasterRequest);
}
