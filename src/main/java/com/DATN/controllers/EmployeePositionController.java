package com.DATN.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DATN.DTO.requestDTO.EmployeePositionRequestDTO;
import com.DATN.DTO.responseDTO.EmployeePositionResponseDTO;
import com.DATN.services.EmployeePositionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/employee-positions")
@RequiredArgsConstructor
public class EmployeePositionController {
    private final EmployeePositionService employeePositionService;

    @PostMapping
    @PreAuthorize("hasAnyRole('HR')")
    public ResponseEntity<EmployeePositionResponseDTO>
            assignPosition(
                    @RequestBody
                    EmployeePositionRequestDTO request) {

        return ResponseEntity.ok(
                employeePositionService
                        .assignPosition(request));
    }

    @PutMapping("/{employeeId}")
    @PreAuthorize("hasAnyRole('HR')")
    public ResponseEntity<EmployeePositionResponseDTO>
            changePosition(
                    @PathVariable Integer employeeId,
                    @RequestBody
                    EmployeePositionRequestDTO request) {

        return ResponseEntity.ok(
                employeePositionService
                        .changePosition(
                                employeeId,
                                request));
    }
}
