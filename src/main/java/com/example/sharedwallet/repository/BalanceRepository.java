package com.example.sharedwallet.repository;

import com.example.sharedwallet.model.Balance;
import com.example.sharedwallet.model.Wallet;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BalanceRepository extends JpaRepository<Balance, Long> {

    List<Balance> findAll(Sort sort);

    Optional<Balance> findByWallet(final Wallet wallet);
}
