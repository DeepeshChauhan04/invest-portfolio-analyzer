package com.sharemarket.invest.repository;

import com.sharemarket.invest.entity.EquityMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquityMasterRepository extends JpaRepository<EquityMaster, Long> {

    @Query(" select e from EquityMaster e " +
            "where e.status = 1 " +
            "and ((coalesce(:isin, null) is null or e.isin in :isin ) " +
            "or (coalesce(:code, null) is  null or e.code in :code ) " +
            "or (coalesce(:series, null) is null or e.series IN :series))")
    List<EquityMaster> findByFilterEqList(@Param("isin") List<String> isin, @Param("code") List<String> code,
                                          @Param("series") List<String> series);

    boolean existsAllByStatus(Integer status);

    List<EquityMaster> findAllByStatus(Integer status);

    boolean existsByEqIdAndStatus(Long eqId, Integer status);

}
