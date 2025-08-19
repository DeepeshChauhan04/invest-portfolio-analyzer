package com.sharemarket.invest.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity(name = "EquityMaster")
@Table(name = "equity_master")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquityMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eq_Id")
    private Long eqId;

    @Column(name = "code")
    private String code;

    @Column(name = "series")
    private String series;

    @Column(name = "isin")
    private String isin;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "sector")
    private String sector;

    @Column(name = "exchange")
    private String exchange;

    @Column(name = "listing_date")
    private LocalDate listingDate;

    @Column(name = "face_value")
    private BigDecimal faceValue;

    @Column(name = "status")
    private Integer status;
}
