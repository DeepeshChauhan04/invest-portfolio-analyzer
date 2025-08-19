package com.sharemarket.invest.repository;

import com.sharemarket.invest.entity.EquityPriceHistory;
import com.sharemarket.invest.entity.EquityTransaction;
import com.sharemarket.invest.entity.MutualFundPriceHistory;
import com.sharemarket.invest.entity.MutualFundTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;


@Repository
public interface MutualFundReportRepository extends JpaRepository<MutualFundPriceHistory, Long> {


    @Query(value = """
               SELECT eph.*
                        FROM mutual_fund_price_history eph
                        INNER JOIN (
                            SELECT code, isin, MAX(nav_date) AS latest_date
                            FROM mutual_fund_price_history
                            WHERE code IN (:codeList)
                              AND isin IN (:isinList)
                              AND nav_date <= :currentDate
                            GROUP BY code, isin
                        ) latest ON eph.code = latest.code
                                 AND eph.isin = latest.isin
                                 AND eph.nav_date = latest.latest_date
             """, nativeQuery = true)
    List<MutualFundPriceHistory> findLatestClosePrice(
            @Param("isinList") Set<String> isinList,
            @Param("codeList") Set<String> codeList,
            LocalDate currentDate
    );

    @Query(value = """
    SELECT eq.*
    FROM mutual_fund_transaction eq
    WHERE eq.status = 1
      AND eq.transaction_date BETWEEN :startDate AND :endDate
      AND eq.portfolio_id IN (:portfolioIds)
      AND eq.isin IN (:isinList)
      AND eq.code IN (:codeList)
    """, nativeQuery = true)
    List<MutualFundTransaction> findAllRequiredSecurities(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("portfolioIds") Set<Long> portfolioIds,
            @Param("isinList") Set<String> isinList,
            @Param("codeList") Set<String> codeList
    );
}
/*
@Query(value = """
    SELECT eq.*
    FROM equity_transaction eq
    WHERE eq.status = 1
      AND eq.transaction_date BETWEEN :startDate AND :endDate
      AND eq.portfolio_id = :portfolioId
      AND eq.isin IN (:isinList)
      AND eq.series IN (:seriesList)
      AND eq.code IN (:codeList)
    """, nativeQuery = true)
List<EquityTransaction> findAllRequiredSecurities(
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate,
        @Param("portfolioId") Long portfolioId,
        @Param("isinList") Set<String> isinList,
        @Param("seriesList") Set<String> seriesList,
        @Param("codeList") Set<String> codeList
);*/
