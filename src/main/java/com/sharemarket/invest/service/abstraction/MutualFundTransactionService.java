package com.sharemarket.invest.service.abstraction;

import com.sharemarket.invest.dto.request.MutualFundTransactionRequest;
import com.sharemarket.invest.dto.response.MutualFundTransactionResponse;

import java.util.List;

public interface MutualFundTransactionService {


    List<MutualFundTransactionResponse> getAllMutualFundTransactionDetails();

    MutualFundTransactionResponse saveTransaction(MutualFundTransactionRequest mutualFundTransactionRequest);

}
