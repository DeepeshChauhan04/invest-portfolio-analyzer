package com.sharemarket.invest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquitySummaryDTO {

    private Integer totalBuyQty;
    private BigDecimal totalBuyValue;
    private Integer totalSellQty;
    private String status;
    private String code;
    private String isin;
    private String series;
    private BigDecimal unrealized;
    private BigDecimal realizedPL;
}
