package com.sharemarket.invest.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "EquityPriceHistory")
@Table(name = "equity_price_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquityPriceHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "series")
    private String series;

    @Column(name = "isin")
    private String isin;

    @Column(name = "trade_date")
    private LocalDate tradeDate;

    @Column(name = "close_price")
    private BigDecimal closePrice;

    @Column(name = "status")
    private Integer status;

    @Column(name = "created_At")
    private LocalDateTime createdAt;

    @Column(name = "updated_At")
    private LocalDateTime updatedAt;
}

