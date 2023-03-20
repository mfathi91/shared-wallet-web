package com.example.sharedwallet.repository;

import com.example.sharedwallet.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Optional<Wallet> findByCurrency(String currency);
}
