package com.example.sharedwallet.repository;

import com.example.sharedwallet.model.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.username != ?1")
    Optional<User> findByUsernameNotEqual(String username);

    default User findByUsernameOrThrow(final String username) {
        return findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No user with name [%s] found!", username)));
    }

    default User findByUsernameNotEqualOrThrow(final String username) {
        return findByUsernameNotEqual(username)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("No user with a different name than [%s] found!", username)));
    }
}