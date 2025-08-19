package com.sharemarket.invest.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "CashManagement")
@Table(name = "cash_management")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CashManagement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "portfolio_id")
    private Long portfolioId;

    @Column(name = "transaction_type")
    private String transactionType;

    @Column(name = "transaction_remark")
    private String transactionRemark;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "transaction_date")
    private LocalDate transactionDate;

    @Column(name = "account_Number")
    private Long accountNumber;

    @Column(name = "status")
    private Integer status;

    @Column(name = "created_At")
    private LocalDateTime createdAt;

    @Column(name = "updated_At")
    private LocalDateTime updatedAt;
}
