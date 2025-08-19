package com.sharemarket.invest.repository;

import com.sharemarket.invest.entity.EquityPriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface EquityPriceHistoryRepository extends JpaRepository<EquityPriceHistory, Long> {


    @Query("select e from EquityPriceHistory e where e.tradeDate = :tradeDate and e.status=1   order by  e.tradeDate desc ")
    List<EquityPriceHistory> findByTradeDateSorted(@Param("tradeDate") LocalDate tradeDate);

    @Query("select e from EquityPriceHistory e where " +
            "e.tradeDate between :startDate and :endDate order by  e.tradeDate DESC")
    List<EquityPriceHistory> findByTradeDateBetweenSorted(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    List<EquityPriceHistory> findAllByStatus(Integer status);

    boolean existsByIdAndStatus(Long id, Integer status);

    @Query("SELECT eph FROM EquityPriceHistory eph WHERE eph.code = :code " +
            "AND eph.isin = :isin AND eph.series = :series and eph.tradeDate <= :currentDate  " +
            "ORDER BY eph.tradeDate desc limit 1")
    List<EquityPriceHistory> findLatestPrice(Set<String> isinList, Set<String> codeList, Set<String> seriesList, LocalDate endDate);


/*

    @Query(value = "SELECT * FROM equity_price_history eph " +
            "WHERE eph.code = :code " +
            "AND eph.isin = :isin " +
            "AND eph.series = :series " +
            "AND eph.trade_date <= :currentDate " +
            "ORDER BY eph.trade_date DESC " +
            "LIMIT 1", nativeQuery = true)
    EquityPriceHistory findLatestPrices(@Param("isin") Set<String> isinList,
                                       @Param("code") Set<String> codeList,
                                       @Param("series") Set<String> seriesList,
                                       @Param("currentDate") LocalDate currentDate);

*/

    @Query(value = "select distinct on (eph.code, eph.isin, eph.series) " +
            "FROM equity_price_history eph WHERE eph.code IN (:codeList) " +
            "AND eph.isin IN (:isinList) AND eph.series IN (:seriesList) " +
            "AND eph.trade_date <= :currentDate ORDER BY " +
            "eph.trade_date DESC limit 1",nativeQuery = true)
    List<EquityPriceHistory> findLatestPrices(@Param("isinList") Set<String> isinList,
                                              @Param("codeList") Set<String> codeList,
                                              @Param("seriesList") Set<String> seriesList,
                                              @Param("currentDate") LocalDate currentDate);


    @Query(value = """
                  SELECT eph.*
            FROM equity_price_history eph
            INNER JOIN (
                SELECT code, isin, series, MAX(trade_date) AS latest_date
                FROM equity_price_history
                WHERE code IN (:codeList)
                  AND isin IN (:isinList)
                  AND series IN (:seriesList)
                  AND trade_date <= :currentDate
                GROUP BY code, isin, series
            ) latest ON eph.code = latest.code
                     AND eph.isin = latest.isin
                     AND eph.series = latest.series
                     AND eph.trade_date = latest.latest_date;;
            """, nativeQuery = true)
    List<EquityPriceHistory> findLatestClosePrice(
            @Param("isinList") Set<String> isinList,
            @Param("codeList") Set<String> codeList,
            @Param("seriesList") Set<String> seriesList,
            LocalDate currentDate
    );




}




