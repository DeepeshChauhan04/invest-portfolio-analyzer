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
public class CashManagementResponse {

    private Long portfolioId;
    private String transactionType;
    private BigDecimal amount;
    private LocalDate transactionDate;
    private Integer status;
}
