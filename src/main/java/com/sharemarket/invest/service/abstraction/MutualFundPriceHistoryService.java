package com.sharemarket.invest.service.abstraction;

import com.sharemarket.invest.dto.response.MutualFundPriceHistoryResponse;

import java.time.LocalDate;
import java.util.List;

public interface MutualFundPriceHistoryService {

    void generateAndInsertHistory();

    List<MutualFundPriceHistoryResponse> getAllListOfMutualFundHistory();

    List<MutualFundPriceHistoryResponse> getByNaveDate(LocalDate date);


}
