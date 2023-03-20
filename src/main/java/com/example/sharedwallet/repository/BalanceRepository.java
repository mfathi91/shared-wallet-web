package com.example.sharedwallet.repository;

import com.example.sharedwallet.model.Balance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceRepository extends JpaRepository<Balance, Long> {
}
