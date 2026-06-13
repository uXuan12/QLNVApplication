package com.DATN.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DATN.entites.Employee;
import com.DATN.entites.LeaveRequest;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Integer>{
    Optional<LeaveRequest> findById(Integer id);
    List<LeaveRequest>
        findByEmployeeOrderByIdDesc(
            Employee employee);

    List<LeaveRequest>
        findByStatusOrderByIdDesc(
            String status);
}
