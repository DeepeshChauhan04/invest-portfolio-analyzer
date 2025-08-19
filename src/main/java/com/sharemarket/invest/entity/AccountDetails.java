package com.sharemarket.invest.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "accounts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_Id")
    private Long accountId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "accountType")
    private String accountType;

    @Column(name = "default_account")
    private Boolean defaultAccount;

    @Column(name = "account_Balance")
    private BigDecimal accountBalance;

    @Column(name = "status")
    private Integer status;

    @Column(name = "created_At")
    private LocalDateTime createdAt;

    @Column(name = "updated_At")
    private LocalDateTime updatedAt;


}
