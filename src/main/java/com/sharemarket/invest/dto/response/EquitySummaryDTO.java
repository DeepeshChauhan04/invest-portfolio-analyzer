package com.sharemarket.invest.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquitySummaryDTO {

    @JsonProperty(value = "total_Buy_Qty")
    private Integer totalBuyQty;

    @JsonProperty(value = "totalBuyValue")
    private BigDecimal totalBuyValue;

    @JsonProperty(value = "totalSellQty")
    private Integer totalSellQty;

    @JsonProperty(value = "status")
    private String status;

    @JsonProperty(value = "code")
    private String code;
    private String isin;
    private String series;
    private BigDecimal unrealized;
    private BigDecimal realizedPL;
}
