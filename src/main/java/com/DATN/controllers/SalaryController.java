package com.DATN.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DATN.DTO.requestDTO.SalaryCalculationRequestDTO;
import com.DATN.DTO.responseDTO.SalaryCalculationResponseDTO;
import com.DATN.DTO.responseDTO.SalaryListResponseDTO;
import com.DATN.DTO.responseDTO.SalaryResponseDTO;
import com.DATN.security.util.JwtUtil;
import com.DATN.services.SalaryService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/salaries")
@RequiredArgsConstructor
public class SalaryController {
    private final SalaryService salaryService;

    private final JwtUtil jwtUtil;

    @GetMapping("/my-payslip")
    public ResponseEntity<SalaryResponseDTO>
            getMyPayslip(
            HttpServletRequest request) {

        String token =
                request.getHeader("Authorization")
                        .substring(7);

        
        Integer accountId =
                jwtUtil.extractAccountId(token);

        SalaryResponseDTO response =
                salaryService
                        .getMyPayslip(accountId);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('HR')")
    public ResponseEntity<List<SalaryListResponseDTO>>
                getAllSalaries() {

        return ResponseEntity.ok(
                salaryService.getAllSalaries());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('HR')")
    public ResponseEntity<SalaryCalculationResponseDTO>
        calculateSalary(@RequestBody SalaryCalculationRequestDTO request) {

      return ResponseEntity.ok(
            salaryService.calculateSalary(
                    request));
    }
}
