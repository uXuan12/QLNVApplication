package com.DATN.services;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.DATN.DTO.requestDTO.EmployeeRequestDTO;
import com.DATN.DTO.responseDTO.EmployeeResponseDTO;
import com.DATN.entites.Account;
import com.DATN.entites.Employee;
import com.DATN.repositories.AccountRepository;
import com.DATN.repositories.EmployeeRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeService {
     private final EmployeeRepository employeeRepository;
     private final AccountRepository accountRepository;

    @Transactional
    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO employeeRequest) {

        
        Account account = accountRepository.findById(
            employeeRequest.getAccountId()).orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản"));
        
        if (employeeRepository.existsByAccount(account)) {
            throw new RuntimeException("Tài khoản đã được gán cho nhân viên khác");
        }
        LocalDate today = LocalDate.now();

        if (employeeRequest.getDob().isAfter(today)) {
            throw new RuntimeException("Ngày sinh không hợp lệ");
        }

        Employee employee = new Employee();

        employee.setAccount(account);
        employee.setFullName(employeeRequest.getFullName().trim());
        employee.setEmail(employeeRequest.getEmail().trim());
        employee.setPhone(employeeRequest.getPhone().trim());
        employee.setDob(employeeRequest.getDob());
        employee.setGender(employeeRequest.getGender());
        employee.setAddress(employeeRequest.getAddress());

        employee.setStatus(
                employeeRequest.getStatus() == null
                        ? "WORKING"
                        : employeeRequest.getStatus());

        Employee savedEmployee = employeeRepository.save(employee);

        return toEmployeeResponseDTO(savedEmployee);
    }

    private EmployeeResponseDTO toEmployeeResponseDTO(
            Employee employee) {

        EmployeeResponseDTO dto = new EmployeeResponseDTO();

        dto.setId(employee.getId());
        dto.setFullName(employee.getFullName());
        dto.setEmail(employee.getEmail());
        dto.setPhone(employee.getPhone());
        dto.setDob(employee.getDob());
        dto.setGender(employee.getGender());
        dto.setAddress(employee.getAddress());
        dto.setStatus(employee.getStatus());

        return dto;
    }
}
