package com.sharemarket.invest.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MutualFundTransactionRequest {

    @JsonProperty(value = "portfolioId")
    @NotNull(message = "Please provide valid portfolio id")
    private Long portfolioId;

    @NotBlank(message = "code must not be blank")
    @JsonProperty(value = "code")
    private String code;

    @NotBlank(message = "isin must not be blank")
    @JsonProperty(value = "isin")
    private String isin;

    @NotBlank(message = "Transaction Type must not be blank")
    @JsonProperty(value = "transactionType")
    private String transactionType;

    @NotNull(message = "price Type must not be null")
    @JsonProperty(value = "nav_pr_unite")
    private BigDecimal navPrUnite;

    @NotNull(message = "unit Type must not be null")
    @JsonProperty(value = "units")
    private Integer units;

    @JsonProperty("valuation_method")
//    @NotBlank(message = "Valuation method is required")
    private String valuationMethod;


}
