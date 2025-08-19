package com.sharemarket.invest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MutualFundTransactionResponse {

    private Long transactionId;
    private Long portfolioId;
    private String code;
    private String isin;
    private String transactionType;
    private Integer units;
    private BigDecimal navPrUnite;
    private BigDecimal totalNav;
    private LocalDate transactionDate;
    private Integer status;
    private String valuationMethod;
}
