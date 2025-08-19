package com.sharemarket.invest.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Test {
/*

    @JsonProperty(value = "details")
    List<PortfolioHoldingsResponse1> details;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class PortfolioHoldingsResponse1 {
        private Long portfolioId;
        List<HoldingSecurityDTO1> holdingSecurityDTO;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class HoldingSecurityDTO1 {
        private String isin;
        private String series;
        private String code;
        List<TransactionDetail1> details;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class TransactionDetail1 {
        private LocalDate buyTransactionDate;
        private LocalDate sellTransactionDate;
        private Integer sellQuantity;
        private BigDecimal buyPrice;
        private BigDecimal sellPrice;
        private BigDecimal costValue;
        private BigDecimal sellValue;
        private BigDecimal realizedGainLoss;
    }
*/

}
