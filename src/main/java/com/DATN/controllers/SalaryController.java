package com.DATN.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
