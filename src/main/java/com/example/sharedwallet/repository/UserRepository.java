package com.example.sharedwallet.repository;

import com.example.sharedwallet.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}