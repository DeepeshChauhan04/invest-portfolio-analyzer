package com.sharemarket.invest.repository;

import com.sharemarket.invest.entity.EquityTransaction;
import com.sharemarket.invest.entity.MutualFundTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface MutualFundTransactionRepository extends JpaRepository<MutualFundTransaction, Long> {



    @Query("SELECT COALESCE(SUM(CASE WHEN e.transactionType = 'BUY' THEN e.units ELSE 0 END), 0)-COALESCE(SUM(CASE WHEN e.transactionType = 'SELL' THEN e.units ELSE 0 END), 0)" +
            "FROM  MutualFundTransaction AS e WHERE e.code = :code  AND e.isin = :isin AND e.portfolioId = :portfolioId AND e.status = 1")
    int getNetAvailableQuantity(@Param("code") String code,
                                @Param("isin") String isin,
                                @Param("portfolioId") Long portfolioId);




    @Query(value = """
    SELECT eq.*
    FROM mutual_fund_transaction eq
    WHERE eq.status = 1
      AND eq.transaction_date BETWEEN :startDate AND :endDate
      AND eq.portfolio_id = :portfolioId
      AND eq.isin IN (:isinList)
      AND eq.code IN (:codeList)
    """, nativeQuery = true)
    List<EquityTransaction> findAllRequiredSecurities(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("portfolioId") Long portfolioId,
            @Param("isinList") Set<String> isinList,
            @Param("codeList") Set<String> codeList
    );

}
