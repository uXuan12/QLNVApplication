package com.DATN.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DATN.entites.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByUsername(String username);
}
