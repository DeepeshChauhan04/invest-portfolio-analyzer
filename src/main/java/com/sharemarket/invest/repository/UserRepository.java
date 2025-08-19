package com.sharemarket.invest.repository;

import com.sharemarket.invest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    boolean existsByUserIdAndStatus(Long userId, Integer status);


}
