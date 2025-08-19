package com.sharemarket.invest.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HoldingsResult {
    private String isin;
    private String series;
    private String code;
    private Long portfolioId;
}