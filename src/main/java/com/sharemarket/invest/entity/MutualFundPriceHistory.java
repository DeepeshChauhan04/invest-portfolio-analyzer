package com.sharemarket.invest.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "MutualFundPriceHistory")
@Table(name = "mutual_fund_price_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MutualFundPriceHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "isin")
    private String isin;

    @Column(name = "nav_date")
    private LocalDate navDate;

    @Column(name = "nav")
    private BigDecimal nav;

    @Column(name = "status")
    private Integer status;

    @Column(name = "created_At")
    private LocalDateTime createdAt;

    @Column(name = "updated_At")
    private LocalDateTime updatedAt;

}

