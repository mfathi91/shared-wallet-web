package com.example.sharedwallet.repository;

import com.example.sharedwallet.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Optional<Wallet> findByCurrency(final String currency);

    default Wallet findByCurrencyOrThrow(final String currency) {
        return findByCurrency(currency)
                .orElseThrow(() -> new IllegalArgumentException(String.format("No wallet with currency [%s] found", currency)));
    }
}
