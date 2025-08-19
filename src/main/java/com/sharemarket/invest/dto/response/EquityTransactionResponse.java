package com.sharemarket.invest.dto.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquityTransactionResponse {

    private Long portfolioId;
    private String code;
    private String series;
    private String isin;
    private String transactionType;
    private Integer quantity;
    private BigDecimal pricePrUnit;
    private BigDecimal totalPrice;
    private LocalDate transactionDate;
    private Integer status;
    private String valuationMethod;

}
