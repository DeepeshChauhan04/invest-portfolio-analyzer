package com.sharemarket.invest.dto.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MutualFundMasterResponse {

    private Long mfId;
    private String code;
    private String isin;
    private String fundName;
    private String fundType;
    private String fundHouse;
    private String category;
    private LocalDate launchDate;
    private Integer status;


}
