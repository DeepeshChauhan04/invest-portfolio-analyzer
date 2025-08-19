package com.sharemarket.invest.repository;

import com.sharemarket.invest.entity.PortfolioDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PortfolioDetailsRepository extends JpaRepository<PortfolioDetails, Long> {


    PortfolioDetails findByPortfolioIdAndStatus(Long portfolioId, Integer status);

    boolean existsByPortfolioIdAndStatus(Long portfolioId, Integer status);

    List<PortfolioDetails> findAllByStatus(Integer status);
}
