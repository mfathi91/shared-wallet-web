package com.example.sharedwallet.repository;

import com.example.sharedwallet.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
