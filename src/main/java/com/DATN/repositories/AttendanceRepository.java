package com.DATN.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DATN.entites.Attendance;
import com.DATN.entites.Employee;

public interface AttendanceRepository extends JpaRepository<Attendance,Integer> {
    boolean existsByEmployeeAndDate(Employee employee,LocalDate date);
    Optional<Attendance>
    findByEmployeeAndDate(
        Employee employee,
        LocalDate date);

    List<Attendance>
    findByEmployeeOrderByDateDesc(
        Employee employee);

    long count();

    long countByStatus(String status);
    long countByEmployeeAndStatusInAndDateBetween(
    Employee employee,
    List<?> statuses,
    LocalDate startDate,
    LocalDate endDate);
}
