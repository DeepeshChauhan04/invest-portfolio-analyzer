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
public class CashManagementRequest {

    @NotNull(message = "Portfolio Id   must not be null")
    @JsonProperty(value = "portfolioId")
    private Long portfolioId;

    @NotBlank(message = "Transaction Type Id   must not be blank")
    @JsonProperty(value = "transaction_type")
    private String transactionType;

    @NotNull(message = "Amount  must not be null")
    @JsonProperty(value = "amount")
    private BigDecimal amount;

    @NotNull(message = "Amount Number  must not be null")
    @JsonProperty(value = "accountNumber")
    private Long accountNumber;

}
