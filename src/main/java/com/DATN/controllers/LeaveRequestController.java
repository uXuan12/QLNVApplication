package com.DATN.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DATN.DTO.requestDTO.LeaveRequestResponseDTO;
import com.DATN.security.util.JwtUtil;
import com.DATN.services.LeaveRequestService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/leave-requests")
@RequiredArgsConstructor
public class LeaveRequestController {
    private final LeaveRequestService leaveRequestService;

    private final JwtUtil jwtUtil;

    @PutMapping("/{id}/approve")
    public ResponseEntity<LeaveRequestResponseDTO>
        approveLeaveRequest(
        @PathVariable Integer id,
        HttpServletRequest request) {

    String header =
            request.getHeader("Authorization");

    String token =
            header.substring(7);

    String role =
            jwtUtil.extractRole(token);

    if (role.equals("EMPLOYEE")) {

        throw new RuntimeException(
            "Bạn không có quyền duyệt đơn");
    }

    Integer managerAccountId =
            jwtUtil.extractAccountId(token);

    LeaveRequestResponseDTO response =
            leaveRequestService
                    .approveLeaveRequest(
                            id,
                            managerAccountId);

    return ResponseEntity.ok(response);
    }
}
