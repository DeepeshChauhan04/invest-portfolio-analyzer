package com.sharemarket.invest.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "PortfolioDetails")
@Table(name = "portfolio_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PortfolioDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_id")
    private Long portfolioId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "portfolio_name")
    private String portfolioName;

    @Column(name = "description")
    private String description;

    @Column(name = "created_At")
    private LocalDateTime createdAt;

    @Column(name = "updated_At")
    private LocalDateTime updatedAt;

    @Column(name = "status")
    private Integer status;
}
