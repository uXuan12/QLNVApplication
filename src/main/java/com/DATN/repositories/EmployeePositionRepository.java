package com.DATN.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DATN.entites.Employee;
import com.DATN.entites.EmployeePosition;

public interface EmployeePositionRepository extends JpaRepository<EmployeePosition, Integer> {
    Optional<EmployeePosition>
            findByEmployeeAndIsActiveTrue(
                    Employee employee);
    
    boolean existsByPositionId(Integer positionId);
    long countByPositionIdAndIsActiveTrue(
        Integer positionId);
}
