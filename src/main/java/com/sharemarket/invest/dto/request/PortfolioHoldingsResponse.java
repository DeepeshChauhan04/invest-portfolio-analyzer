package com.sharemarket.invest.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class PortfolioHoldingsResponse {
    @JsonProperty(value = "details")
    List<PortfolioWiseResponse> details;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class PortfolioWiseResponse {
        private Long portfolioId;
        List<HoldingSecurityDTO> holdingSecurityDTO;
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class HoldingSecurityDTO {
        private String isin;
        private String series;
        private String code;
        List<TransactionDetail> detail;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class TransactionDetail {
        private LocalDate buyTransactionDate;
        private LocalDate sellTransactionDate;
        private Integer sellQuantity;
        private BigDecimal buyPrice;
        private BigDecimal sellPrice;
        private BigDecimal costValue;
        private BigDecimal sellValue;
        private BigDecimal realizedGainLoss;
    }

}


/*

    private Long portfolioId;
    List<HoldingSecurityDTO> holdingSecurityDTO;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class HoldingSecurityDTO {
        private String isin;
        private String series;
        private String code;
        List<TransactionDetail> details;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class TransactionDetail {
        private LocalDate buyTransactionDate;
        private LocalDate sellTransactionDate;
        private Integer sellQuantity;
        private BigDecimal buyPrice;
        private BigDecimal sellPrice;
        private BigDecimal costValue;
        private BigDecimal sellValue;
        private BigDecimal realizedGainLoss;
    }

}
*/
