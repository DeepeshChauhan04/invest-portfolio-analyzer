package com.sharemarket.invest.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquityTransactionRequest {


    @JsonProperty(value = "portfolio_id")
    @NotNull(message = "Please provide valid portfolio id")
    private Long portfolioId;

    @NotBlank(message = "code must not be blank")
    @JsonProperty(value = "code")
    private String code;

    @NotBlank(message = "series must not be blank")
    @JsonProperty(value = "series")
    private String series;

    @NotBlank(message = "isin must not be blank")
    @JsonProperty(value = "isin")
    private String isin;

    @NotNull(message = "Price must not be null")
    @JsonProperty(value = "pricePrUnit")
    private BigDecimal pricePrUnit;

    @NotBlank(message = "Transaction Type must not be blank")
    @JsonProperty(value = "transaction_type")
    private String transactionType;

    @NotNull(message = "Quantity must not be null")
    @JsonProperty(value = "quantity")
    private Integer quantity;

    @JsonProperty("valuation_method")
   // @NotBlank(message = "Valuation method is required")
    private String valuationMethod;


}
