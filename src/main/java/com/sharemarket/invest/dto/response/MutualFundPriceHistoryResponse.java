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
public class MutualFundPriceHistoryResponse {
    private Long id;
    private String code;
    private String isin;
    private LocalDate navDate;
    private BigDecimal nav;
    private Integer status;
   }
