package com.DATN.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DATN.DTO.responseDTO.AttendanceRateResponseDTO;
import com.DATN.DTO.responseDTO.WorkforceChartResponseDTO;
import com.DATN.services.ReportService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/workforce-chart")
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public ResponseEntity<
            List<WorkforceChartResponseDTO>>
            getWorkforceChart() {

        return ResponseEntity.ok(
                reportService
                        .getWorkforceChart());
    }

    @GetMapping("/attendance-rate")
    @PreAuthorize("hasAnyRole('HR')")
    public ResponseEntity<
            AttendanceRateResponseDTO>
            getAttendanceRate() {

        return ResponseEntity.ok(
                reportService
                        .getAttendanceRate());
    }
}
