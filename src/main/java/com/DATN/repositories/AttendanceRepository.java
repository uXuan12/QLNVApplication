package com.DATN.repositories;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DATN.entites.Attendance;
import com.DATN.entites.Employee;

public interface AttendanceRepository extends JpaRepository<Attendance,Integer> {
    boolean existsByEmployeeAndDate(Employee employee,LocalDate date);
}
