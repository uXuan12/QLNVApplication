package com.DATN.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DATN.entites.Salary;

public interface SalaryRepository extends JpaRepository<Salary, Integer>{
    Optional<Salary> findTopByEmployee_IdOrderBySalaryYearDescSalaryMonthDesc(Integer employeeId);
    List<Salary>
    findAllByOrderBySalaryYearDescSalaryMonthDesc();
}
