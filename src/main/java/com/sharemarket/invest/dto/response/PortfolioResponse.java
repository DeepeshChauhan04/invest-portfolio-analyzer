package com.sharemarket.invest.dto.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PortfolioResponse {

    private Long portfolioId;
    private Long userId;
    private String portfolioName;
    private String description;
    private LocalDateTime createdAt;
    private Integer status;
}
