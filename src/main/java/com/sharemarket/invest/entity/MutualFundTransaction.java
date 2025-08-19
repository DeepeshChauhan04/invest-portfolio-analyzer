package com.sharemarket.invest.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "MutualFundTransaction")
@Table(name = "mutual_fund_transaction")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MutualFundTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long transactionId;
    @NotNull
    @Column(name = "portfolio_id")
    private Long portfolioId;

    @Column(name = "code")
    private String code;

    @Column(name = "isin")
    private String isin;

    @Column(name = "transaction_type")
    private String transactionType;

    @Column(name = "units")
    private Integer units;

    @Column(name = "nav_pr_unite")
    private BigDecimal navPrUnite;

    @Column(name = "total_nav")
    private BigDecimal totalNav;

    @Column(name = "transaction_date")
    private LocalDate transactionDate;

    @Column(name = "created_At")
    private LocalDateTime createdAt;

    @Column(name = "valuation_Method")
    private String valuationMethod;



    @Column(name = "updated_At")
    private LocalDateTime updatedAt;

    @Column(name = "status")
    private Integer status;
}
