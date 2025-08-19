package com.sharemarket.invest.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "MutualFundMaster")
@Table(name = "mutual_fund_master")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MutualFundMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mf_id")
    private Long mfId;
    @NotNull
    @Column(name = "code")
    private String code;

    @Column(name = "isin")
    private String isin;

    @Column(name = "fund_name")
    private String fundName;

    @Column(name = "fund_type")
    private String fundType;

    @Column(name = "fund_house")
    private String fundHouse;

    @Column(name = "category")
    private String category;

    @Column(name = "launch_date")
    private LocalDate launchDate;

    @Column(name = "status")
    private Integer status;

    @Column(name = "created_At")
    private LocalDateTime createdAt;

    @Column(name = "updated_At")
    private LocalDateTime updatedAt;
}

