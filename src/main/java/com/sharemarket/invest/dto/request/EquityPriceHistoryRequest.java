package com.sharemarket.invest.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquityPriceHistoryRequest {

    @NotBlank(message = "code must not be blank")
    @JsonProperty(value= "code")
    private String code;

    @NotBlank(message = "Series must not be blank")
    @JsonProperty(value = "series")
    private String series;

    @NotBlank(message = "isin must not be blank")
    @JsonProperty(value = "isin")
    private String isin;

    @NotNull(message = "Trade Date must not be null")
    @JsonProperty(value = "trade_date")
    private LocalDate tradeDate;


    @NotNull(message = "Close Price  must not be null")
    @JsonProperty(value = "close_price")
    private BigDecimal closePrice;
}
