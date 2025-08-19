package com.sharemarket.invest.service.abstraction;

import com.sharemarket.invest.dto.request.EquityMasterRequest;
import com.sharemarket.invest.dto.response.EquityMasterResponse;

import java.util.List;

public interface EquityMasterService {


    List<EquityMasterResponse> getAllEquities();

    List<EquityMasterResponse> getFilterEquitiesList(EquityMasterRequest request);

    EquityMasterResponse getByEqId(Long id);

    void generateEquityMaster();
}
