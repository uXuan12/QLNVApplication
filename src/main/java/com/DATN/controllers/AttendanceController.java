package com.DATN.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DATN.DTO.responseDTO.AttendanceCheckOutResponseDTO;
import com.DATN.DTO.responseDTO.AttendanceHistoryResponseDTO;
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

    @PostMapping("/check-out")
        public ResponseEntity<
                AttendanceCheckOutResponseDTO>
                checkOut(
                HttpServletRequest request) {

        String token =
                request.getHeader(
                        "Authorization")
                        .substring(7);

        return ResponseEntity.ok(
                attendanceService.checkOut(
                        token));
    }

    @GetMapping("/my-history")
    public ResponseEntity<
                List<AttendanceHistoryResponseDTO>>
                getMyHistory(
                HttpServletRequest request) {

        String token =
                request.getHeader(
                        "Authorization")
                        .substring(7);

        return ResponseEntity.ok(
                attendanceService.getMyHistory(
                        token));
    }
        
}
