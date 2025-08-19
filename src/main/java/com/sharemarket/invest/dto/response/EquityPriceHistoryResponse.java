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
public class EquityPriceHistoryResponse {
    private Long id;
    private String code;
    private String series;
    private String isin;
    private LocalDate tradeDate;
    private BigDecimal closePrice;
    private Integer status;

}
