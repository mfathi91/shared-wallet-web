package com.example.sharedwallet.repository;

import com.example.sharedwallet.model.Balance;
import com.example.sharedwallet.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BalanceRepository extends JpaRepository<Balance, Long> {

    Optional<Balance> findByWallet(final Wallet wallet);
}
