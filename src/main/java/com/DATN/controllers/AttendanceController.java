package com.DATN.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DATN.DTO.responseDTO.AttendanceResponseDTO;
import com.DATN.services.AttendanceService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {
    private final AttendanceService attendanceService;

    @PostMapping("/check-in")
    public ResponseEntity<AttendanceResponseDTO>
            checkIn(
            HttpServletRequest request) {

        String token =
                request.getHeader("Authorization")
                        .substring(7);

        AttendanceResponseDTO response =
                attendanceService.checkIn(token);

        return ResponseEntity.ok(response);
    }
}
