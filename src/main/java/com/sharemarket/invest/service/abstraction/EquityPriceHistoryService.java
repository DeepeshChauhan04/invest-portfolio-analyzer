package com.sharemarket.invest.service.abstraction;

import com.sharemarket.invest.dto.response.EquityPriceHistoryResponse;

import java.time.LocalDate;
import java.util.List;

public interface EquityPriceHistoryService {

    void generateAndInsertHistory();

    List<EquityPriceHistoryResponse> getALlEquityPriceHistory();

    List<EquityPriceHistoryResponse> getByTradeDate(LocalDate trade_date);

    void deleteEquityPriceHistory(Long id);
    // List<EquityPriceHistoryResponse> getByTradeDateRange(LocalDate start, LocalDate end) ;
}
