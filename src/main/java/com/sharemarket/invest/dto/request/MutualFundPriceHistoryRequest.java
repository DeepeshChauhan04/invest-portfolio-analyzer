package com.sharemarket.invest.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MutualFundPriceHistoryRequest {

    @NotBlank(message = "code must not be blank")
    @JsonProperty(value = "code")
    private String code;

    @NotBlank(message = "code must not be blank")
    @JsonProperty(value = "isin")
    private String isin;

    @NotNull(message = "Nav Date must not be null")
    @JsonProperty(value = "nav_date")
    private LocalDate navDate;

    @NotBlank(message = "nav must not be blank")
    @JsonProperty(value = "nav")
    private BigDecimal nav;
}

