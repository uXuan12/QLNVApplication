package com.DATN.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DATN.entites.Account;
import com.DATN.entites.Employee;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
    Optional<Employee> findByAccount(Account account);
    boolean existsByAccount(Account account);
    Optional<Employee> findByAccountId(Integer accountId);
}
