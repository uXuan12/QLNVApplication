package com.DATN.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.DATN.entites.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer>{
    boolean existsByName(String name);
    Optional<Department> findByName(String name);
    
}
