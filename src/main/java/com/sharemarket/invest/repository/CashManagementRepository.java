package com.sharemarket.invest.repository;


import com.sharemarket.invest.entity.CashManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CashManagementRepository extends JpaRepository<CashManagement, Long> {

    List<CashManagement> findByAccountNumber(Long accountNo);

    List<CashManagement> findByPortfolioId(Long portfolioId);


}
