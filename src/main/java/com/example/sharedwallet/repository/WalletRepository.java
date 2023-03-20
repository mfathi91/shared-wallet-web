package com.example.sharedwallet.repository;

import com.example.sharedwallet.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Wallet findByCurrency(String currency);
}
