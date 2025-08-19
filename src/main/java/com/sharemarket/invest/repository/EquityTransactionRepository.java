package com.sharemarket.invest.repository;

import com.sharemarket.invest.dto.response.EquitySummaryView;
import com.sharemarket.invest.entity.EquityTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface EquityTransactionRepository extends JpaRepository<EquityTransaction, Long> {

    List<EquityTransaction> findAllByStatus(Integer status);


    @Query("SELECT COALESCE(SUM(CASE WHEN e.transactionType = 'BUY' THEN e.quantity ELSE 0 END), 0)-COALESCE(SUM(CASE WHEN e.transactionType = 'SELL' THEN e.quantity ELSE 0 END), 0)" +
            "FROM  EquityTransaction AS e WHERE e.code = :code AND e.series = :series AND e.isin = :isin AND e.portfolioId = :portfolioId AND e.status = 1")
    Integer getNetAvailableQuantity(@Param("code") String code,
                                    @Param("series") String series,
                                    @Param("isin") String isin,
                                    @Param("portfolioId") Long portfolioId);

    @Query("""
                SELECT 
                    COALESCE(SUM(CASE WHEN e.transactionType = 'BUY' THEN e.quantity ELSE 0 END), 0) AS totalBuyQty,
                    COALESCE(SUM(CASE WHEN e.transactionType = 'BUY' THEN e.pricePrUnit * e.quantity ELSE 0 END), 0) AS totalBuyValue,
                    COALESCE(SUM(CASE WHEN e.transactionType = 'SELL'  THEN e.quantity ELSE 0 END), 0) AS totalSellQty,
                   COALESCE(SUM(CASE WHEN e.transactionType = 'SELL'  THEN e.quantity*e.pricePrUnit ELSE 0 END), 0) AS totalSellValue
            
                FROM EquityTransaction e
                WHERE e.code = :code AND e.series = :series AND e.isin = :isin
                  AND e.portfolioId = :portfolioId AND e.status = 1
            """)
    EquitySummaryView getEquitySummary(
            @Param("code") String code,
            @Param("series") String series,
            @Param("isin") String isin,
            @Param("portfolioId") Long portfolioId
    );

    @Query("SELECT e FROM EquityTransaction e WHERE e.code = :code AND e.series = :series AND e.isin = :isin AND e.portfolioId = :portfolioId AND e.status = 1")
    List<EquityTransaction> findAllByCodeAndPortfolio(
            @Param("code") String code,
            @Param("series") String series,
            @Param("isin") String isin,
            @Param("portfolioId") Long portfolioId
    );

    @Query("SELECT e FROM EquityTransaction e WHERE e.valuationMethod = :valuationMethod AND e.status = 1")
    List<EquityTransaction> findLIFOTransaction(@Param("valuationMethod") String valuationMethod);


    @Query("SELECT e FROM EquityTransaction e WHERE e.code = :code AND e.series = :series AND e.isin = :isin AND e.portfolioId = :portfolioId " +
            "AND (e.transactionType = 'BUY' OR (e.transactionType = 'SELL' AND e.valuationMethod = 'LIFO')) " +
            "ORDER BY e.transactionDate ASC, e.transactionId ASC")
    List<EquityTransaction> findLifoTransactions(@Param("code") String code,
                                                 @Param("series") String series,
                                                 @Param("isin") String isin,
                                                 @Param("portfolioId") Long portfolioId);


    @Query("SELECT e FROM EquityTransaction e WHERE e.code = :code AND e.series = :series AND e.isin = :isin AND e.portfolioId = :portfolioId " +
            "AND (e.transactionType = 'BUY' OR (e.transactionType = 'SELL' AND e.valuationMethod = 'FIFO')) " +
            "ORDER BY e.transactionDate ASC, e.transactionId ASC")
    List<EquityTransaction> findFifoTransaction(String code, String series, String isin, Long portfolioId);

    @Query("""
                SELECT 
               COALESCE(SUM(CASE WHEN e.transactionType = 'BUY' THEN e.quantity ELSE 0 END), 0) AS totalBuyQty,
                COALESCE(SUM(CASE WHEN e.transactionType = 'BUY' THEN e.pricePrUnit * e.quantity ELSE 0 END), 0) AS totalBuyValue,
                    COALESCE(SUM(CASE WHEN e.transactionType = 'SELL' AND e.valuationMethod = 'FIFO' THEN e.quantity ELSE 0 END), 0) AS totalSellQty,
                    COALESCE(SUM(CASE WHEN e.transactionType = 'SELL' AND e.valuationMethod = 'FIFO' THEN e.quantity*e.pricePrUnit ELSE 0 END), 0) AS totalSellValue
            
                FROM EquityTransaction e
                WHERE e.code = :code AND e.series = :series AND e.isin = :isin
                  AND e.portfolioId = :portfolioId AND e.status = 1
            """)
    EquitySummaryView getEquityTransactionByFifo(
            @Param("code") String code,
            @Param("series") String series,
            @Param("isin") String isin,
            @Param("portfolioId") Long portfolioId
    );


    List<EquityTransaction> findByIsinAndSeriesAndCodeAndPortfolioIdOrderByTransactionDateAsc(
            @Param("code") String code,
            @Param("isin") String isin,
            @Param("series") String series,
            @Param("portfolioId") Long portfolioId
    );

    /*@Query(value = """
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
/*    @Query("""
                SELECT eq
                FROM EquityTransaction eq
                WHERE eq.status = 1
                  and eq.transactionDate BETWEEN coalesce(:startDate, eq.transactionDate) AND coalesce(:endDate, eq.transactionDate)
                  AND eq.portfolioId = :portfolioId
                  and (coalesce(:isinList, null) IS NULL OR eq.isin IN :isinList)
                  AND (coalesce(:seriesList, null) IS NULL OR eq.series IN :seriesList)
                  AND (coalesce(:codeList, null) IS NULL OR eq.code IN :codeList)
            """)
    List<EquityTransaction> findAllRequiredSecurities(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("portfolioId") Long portfolioId,
            @Param("isinList") Set<String> isinList,
            @Param("seriesList") Set<String> seriesList,
            @Param("codeList") Set<String> codeList
    );*/
    @Query("""
    SELECT eq
    FROM EquityTransaction eq
    WHERE eq.status = 1
      AND eq.transactionDate BETWEEN COALESCE(:startDate, eq.transactionDate) 
                                 AND COALESCE(:endDate, eq.transactionDate)
      AND (COALESCE(:portfolioIds, null) IS NULL OR eq.portfolioId IN :portfolioIds)
      AND (COALESCE(:isinList, null) IS NULL OR eq.isin IN :isinList)
      AND (COALESCE(:seriesList, null) IS NULL OR eq.series IN :seriesList)
      AND (COALESCE(:codeList, null) IS NULL OR eq.code IN :codeList)
""")
    List<EquityTransaction> findAllRequiredSecurities(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("portfolioIds") Set<Long> portfolioIds,
            @Param("isinList") Set<String> isinList,
            @Param("seriesList") Set<String> seriesList,
            @Param("codeList") Set<String> codeList
    );



}