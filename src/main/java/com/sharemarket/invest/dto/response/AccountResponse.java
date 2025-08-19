package com.sharemarket.invest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {

    private Long accountId;

    private Long userId;

    private String accountType;

    private BigDecimal accountBalance;

    private Integer status;

    private Boolean defaultAccount;

}
