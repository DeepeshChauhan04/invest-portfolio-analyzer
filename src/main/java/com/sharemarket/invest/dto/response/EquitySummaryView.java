package com.sharemarket.invest.dto.response;

import java.math.BigDecimal;

public interface EquitySummaryView {
    Integer getTotalBuyQty();
    BigDecimal getTotalBuyValue();
    Integer getTotalSellQty();
    BigDecimal getTotalSellValue();
}
