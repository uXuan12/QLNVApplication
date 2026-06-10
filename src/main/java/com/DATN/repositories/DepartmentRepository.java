package com.DATN.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.DATN.entites.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer>{

}
