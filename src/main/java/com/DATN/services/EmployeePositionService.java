package com.DATN.services;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.DATN.DTO.requestDTO.EmployeePositionRequestDTO;
import com.DATN.DTO.responseDTO.EmployeePositionResponseDTO;
import com.DATN.entites.Department;
import com.DATN.entites.Employee;
import com.DATN.entites.EmployeePosition;
import com.DATN.entites.Position;
import com.DATN.repositories.EmployeePositionRepository;
import com.DATN.repositories.EmployeeRepository;
import com.DATN.repositories.PositionRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeePositionService {
    private final EmployeeRepository employeeRepository;

    private final PositionRepository positionRepository;

    private final EmployeePositionRepository employeePositionRepository;

    @Transactional
    public EmployeePositionResponseDTO
            assignPosition(
                    EmployeePositionRequestDTO request) {

        Employee employee =
                employeeRepository
                        .findById(
                                request.getEmployeeId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Không tìm thấy nhân viên"));

        Position position =
                positionRepository
                        .findById(
                                request.getPositionId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Không tìm thấy chức vụ"));

        if (employeePositionRepository
                .findByEmployeeAndIsActiveTrue(
                        employee)
                .isPresent()) {

            throw new RuntimeException(
                    "Nhân viên đã có chức vụ đang hoạt động");
        }

        if (request.getStartDate() == null) {

            throw new RuntimeException(
                    "Ngày bắt đầu không được để trống");
        }

        EmployeePosition assignment =
                new EmployeePosition();

        assignment.setEmployee(employee);

        assignment.setPosition(position);

        assignment.setStartDate(
                request.getStartDate());

        assignment.setIsActive(true);

        EmployeePosition saved =
                employeePositionRepository
                        .save(assignment);

        return toResponseDTO(saved);
    }

    @Transactional
    public EmployeePositionResponseDTO
            changePosition(
                    Integer employeeId,
                    EmployeePositionRequestDTO request) {

        Employee employee =
                employeeRepository
                        .findById(employeeId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Không tìm thấy nhân viên"));

        Position newPosition =
                positionRepository
                        .findById(
                                request.getPositionId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Không tìm thấy chức vụ"));

        EmployeePosition currentAssignment =
                employeePositionRepository
                        .findByEmployeeAndIsActiveTrue(
                                employee)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Nhân viên chưa có chức vụ"));

        currentAssignment.setIsActive(false);

        currentAssignment.setEndDate(
                LocalDate.now());

        employeePositionRepository
                .save(currentAssignment);

        EmployeePosition newAssignment =
                new EmployeePosition();

        newAssignment.setEmployee(employee);

        newAssignment.setPosition(
                newPosition);

        newAssignment.setStartDate(
                request.getStartDate());

        newAssignment.setIsActive(true);

        EmployeePosition saved =
                employeePositionRepository
                        .save(newAssignment);

        return toResponseDTO(saved);
    }

    private EmployeePositionResponseDTO
            toResponseDTO(
                    EmployeePosition employeePosition) {

        EmployeePositionResponseDTO dto =
                new EmployeePositionResponseDTO();

        Employee employee =
                employeePosition.getEmployee();

        Position position =
                employeePosition.getPosition();

        Department department =
                position.getDepartment();

        dto.setEmployeeId(
                employee.getId());

        dto.setEmployeeName(
                employee.getFullName());

        dto.setPositionId(
                position.getId());

        dto.setPositionTitle(
                position.getTitle());

        dto.setDepartmentId(
                department.getId());

        dto.setDepartmentName(
                department.getName());

        dto.setStartDate(
                employeePosition.getStartDate());

        dto.setEndDate(
                employeePosition.getEndDate());

        dto.setActive(
                employeePosition.getIsActive());

        return dto;
    }
}
