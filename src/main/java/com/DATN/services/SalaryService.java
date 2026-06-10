package com.DATN.services;

import org.springframework.stereotype.Service;

import com.DATN.DTO.responseDTO.SalaryResponseDTO;
import com.DATN.entites.Employee;
import com.DATN.entites.Salary;
import com.DATN.repositories.EmployeeRepository;
import com.DATN.repositories.SalaryRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
@Transactional
public class SalaryService {
    private final EmployeeRepository employeeRepository;

    private final SalaryRepository salaryRepository;

    public SalaryResponseDTO getMyPayslip(
            Integer accountId) {

        Employee employee =
                employeeRepository
                        .findByAccountId(accountId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Không tìm thấy nhân viên"));

        
        Salary salary =
                salaryRepository
                        .findTopByEmployee_IdOrderBySalaryYearDescSalaryMonthDesc(
                                employee.getId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Chưa có phiếu lương"));

        return mapToResponseDTO(salary);
    }

    private SalaryResponseDTO mapToResponseDTO(
            Salary salary) {

        SalaryResponseDTO dto =
                new SalaryResponseDTO();

        dto.setId(salary.getId());

        dto.setEmployeeId(
                salary.getEmployee().getId());

        dto.setSalaryMonth(
                salary.getSalaryMonth());

        dto.setSalaryYear(
                salary.getSalaryYear());

        dto.setBasicSalary(
                salary.getBasicSalary());

        dto.setAllowance(
                salary.getAllowance());

        dto.setBonus(
                salary.getBonus());

        dto.setDeduction(
                salary.getDeduction());

        dto.setNetSalary(
                salary.getNetSalary());

        dto.setPaymentDate(
                salary.getPaymentDate());

        dto.setStatus(
                salary.getStatus());

        return dto;
    }
}
