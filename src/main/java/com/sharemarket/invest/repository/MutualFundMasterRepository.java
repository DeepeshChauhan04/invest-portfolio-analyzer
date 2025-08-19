package com.sharemarket.invest.repository;

import com.sharemarket.invest.entity.MutualFundMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MutualFundMasterRepository extends JpaRepository<MutualFundMaster, Long> {


    @Query(" " +
            "select m from MutualFundMaster m " +
            "where m.status = 1" +
            "AND(:isin is not null and m.isin in :isin) " +
            "or (:code is not null and m.code in :code) ")
    List<MutualFundMaster> getFilterListOfMutualFunds(@Param("isin") List<String> isin,
                                                      @Param("code") List<String> code);


}
