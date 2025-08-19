package com.sharemarket.invest.repository;


import com.sharemarket.invest.entity.EquityPriceHistory;
import com.sharemarket.invest.entity.MutualFundPriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface MutualFundPriceHistoryRepository extends JpaRepository<MutualFundPriceHistory, Long> {

    @Query("select m from MutualFundPriceHistory m where m.navDate = :navDate and m.status=1 order by  m.navDate desc ")
    List<MutualFundPriceHistory> findByNavDate(@Param("navDate") LocalDate navDate);




}
