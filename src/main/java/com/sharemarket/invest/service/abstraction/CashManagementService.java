package com.sharemarket.invest.service.abstraction;

import com.sharemarket.invest.dto.request.CashManagementRequest;
import com.sharemarket.invest.dto.response.CashManagementResponse;

import java.util.List;

public interface CashManagementService {

    CashManagementResponse saveCashTransaction(CashManagementRequest cashManagementRequest);

    List<CashManagementResponse> fetchAllCashTransaction();

    List<CashManagementResponse> fetchCashTransactionByPortfolioId(Long id);
}
