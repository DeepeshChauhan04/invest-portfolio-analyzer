package com.sharemarket.invest.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "EquityTransaction")
@Table(name = "equity_transaction")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquityTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long transactionId;

    @Column(name = "portfolio_id")
    private Long portfolioId;

    @Column(name = "code")
    private String code;

    @Column(name = "series")
    private String series;

    @Column(name = "isin")
    private String isin;

    @Column(name = "transaction_type")
    private String transactionType;

    @Column(name = "quantity")
    private Integer quantity;


    @Column(name = "price_pr_unit")
    private BigDecimal pricePrUnit;

    @Column(name = "toatal_price")
    private BigDecimal totalPrice;

    @Column(name = "transaction_date")
    private LocalDate transactionDate;

    @Column(name = "created_At")
    private LocalDateTime createdAt;

    @Column(name = "updated_At")
    private LocalDateTime updatedAt;

    @Column(name = "status")
    private Integer status;

    @Column(name = "valuation_Method")
    private String valuationMethod;




}

