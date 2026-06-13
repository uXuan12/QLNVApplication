package com.DATN.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.DATN.DTO.requestDTO.EmployeeRequestDTO;
import com.DATN.DTO.requestDTO.EmployeeUpdateRequestDTO;
import com.DATN.DTO.responseDTO.EmployeeListResponseDTO;
import com.DATN.DTO.responseDTO.EmployeeResponseDTO;
import com.DATN.entites.Account;
import com.DATN.entites.Employee;
import com.DATN.entites.EmployeePosition;
import com.DATN.entites.Position;
import com.DATN.repositories.AccountRepository;
import com.DATN.repositories.EmployeePositionRepository;
import com.DATN.repositories.EmployeeRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final AccountRepository accountRepository;
    private final EmployeePositionRepository employeePositionRepository;

    public List<EmployeeListResponseDTO> getAllEmployees() {

        List<Employee> employees =
                employeeRepository.findAll();

        return employees.stream()
                .map(this::toListResponseDTO)
                .collect(Collectors.toList());
    }
    
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

    @Transactional
    public EmployeeResponseDTO updateEmployee(
            Integer employeeId,
            EmployeeUpdateRequestDTO request) {

        Employee employee =
                employeeRepository.findById(employeeId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Không tìm thấy nhân viên"));

        LocalDate today = LocalDate.now();

        if (request.getDob().isAfter(today)) {

            throw new RuntimeException(
                    "Ngày sinh không hợp lệ");
        }

        Employee emailOwner =
                employeeRepository
                        .findByEmail(request.getEmail())
                        .orElse(null);

        if (emailOwner != null
                && !emailOwner.getId()
                        .equals(employeeId)) {

            throw new RuntimeException(
                    "Email đã tồn tại");
        }

        employee.setFullName(
                request.getFullName().trim());

        employee.setEmail(
                request.getEmail().trim());

        employee.setPhone(
                request.getPhone().trim());

        employee.setDob(
                request.getDob());

        employee.setGender(
                request.getGender());

        employee.setAddress(
                request.getAddress());

        employee.setStatus(
                request.getStatus());

        Employee updatedEmployee =
                employeeRepository.save(employee);

        return toEmployeeResponseDTO(
                updatedEmployee);
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
    
    private EmployeeListResponseDTO
            toListResponseDTO(Employee employee) {

        EmployeeListResponseDTO dto =
                new EmployeeListResponseDTO();

        dto.setId(employee.getId());
        dto.setFullName(employee.getFullName());
        dto.setEmail(employee.getEmail());
        dto.setPhone(employee.getPhone());
        dto.setDob(employee.getDob());
        dto.setGender(employee.getGender());
        dto.setAddress(employee.getAddress());
        dto.setStatus(employee.getStatus());

        if (employee.getAccount() != null) {

            dto.setUsername(
                    employee.getAccount()
                            .getUsername());

            if (employee.getAccount().getRole() != null) {

                dto.setRole(
                        employee.getAccount()
                                .getRole()
                                .getName());
            }
        }

        EmployeePosition employeePosition =
                employeePositionRepository
                        .findByEmployeeAndIsActiveTrue(
                                employee)
                        .orElse(null);

        if (employeePosition != null
                && employeePosition.getPosition() != null) {

            Position position =
                    employeePosition.getPosition();

            dto.setPosition(
                    position.getTitle());

            if (position.getDepartment() != null) {

                dto.setDepartment(
                        position.getDepartment()
                                .getName());
            }
        }

        return dto;
    }
}
