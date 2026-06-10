package com.DATN.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DATN.DTO.requestDTO.EmployeeRequestDTO;
import com.DATN.DTO.responseDTO.EmployeeResponseDTO;
import com.DATN.services.EmployeeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeResponseDTO>
            createEmployee(
            @Valid @RequestBody EmployeeRequestDTO employeeRequest) {

        EmployeeResponseDTO savedEmployee =
                employeeService.createEmployee(employeeRequest);

        return ResponseEntity.ok(savedEmployee);
    }
}
