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
public class AccountRequest {

    @NotNull(message = "User Id must not be null")
    @JsonProperty(value = "userId")
    private Long userId;

    @NotBlank(message = "Account Type  must not be blank")
    @JsonProperty(value = "accountType")
    private String accountType;

    @NotNull(message = "Account Balance  must not be null")
    @JsonProperty(value = "account_Balance")
    private BigDecimal accountBalance;

    @JsonProperty(value = "account_Id")
    private Long accountId;
}
