package com.DATN.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DATN.DTO.requestDTO.EmployeeRequestDTO;
import com.DATN.DTO.requestDTO.EmployeeUpdateRequestDTO;
import com.DATN.DTO.responseDTO.EmployeeListResponseDTO;
import com.DATN.DTO.responseDTO.EmployeeResponseDTO;
import com.DATN.services.EmployeeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping
    @PreAuthorize("hasAnyRole('HR')")
    public ResponseEntity<List<EmployeeListResponseDTO>>
            getAllEmployees() {

        return ResponseEntity.ok(
                employeeService.getAllEmployees());
    }
    
    @PostMapping
    @PreAuthorize("hasAnyRole('HR')")
    public ResponseEntity<EmployeeResponseDTO>
            createEmployee(
            @Valid @RequestBody EmployeeRequestDTO employeeRequest) {

        EmployeeResponseDTO savedEmployee =
                employeeService.createEmployee(employeeRequest);

        return ResponseEntity.ok(savedEmployee);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('HR')")
    public ResponseEntity<EmployeeResponseDTO>
            updateEmployee(
                    @PathVariable Integer id,
                    @Valid @RequestBody
                    EmployeeUpdateRequestDTO request) {

        return ResponseEntity.ok(
                employeeService.updateEmployee(
                        id,
                        request));
    }
}
