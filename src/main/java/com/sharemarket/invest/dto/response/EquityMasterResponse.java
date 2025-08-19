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
public class EquityMasterResponse {
    private Long eqId;
    private String code;
    private String series;
    private String isin;
    private String companyName;
    private String sector;
    private String exchange;
    private LocalDate listingDate;
    private BigDecimal faceValue;
    private Integer status;

}
