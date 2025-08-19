package com.sharemarket.invest.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomInvestException extends RuntimeException {

    private Integer status;
    private String message;
    private String messageCode;

}
