package com.sharemarket.invest.repository;

import com.sharemarket.invest.entity.AccountDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountDetails, Long> {

    List<AccountDetails> findAllByStatus(Integer status);

    boolean existsByAccountIdAndStatus(Long accountId, Integer status);

    AccountDetails findByAccountIdAndStatus(Long accountId, Integer status);

    Optional<AccountDetails> findByUserIdAndDefaultAccountTrue(Long userId);

    List<AccountDetails> findAccountDetailsByUserIdAndStatus(Long userId, Integer status);

    @Query("select a from AccountDetails a " +
            "join PortfolioDetails p on p.userId = a.userId " +
            "where p.portfolioId = :portfolioId and a.defaultAccount = true")
    Optional<AccountDetails> findDefaultAccountByPortfolioId(@Param("portfolioId") Long portfolioId);



}

