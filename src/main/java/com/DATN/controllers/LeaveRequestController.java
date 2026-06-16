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

import com.DATN.DTO.requestDTO.LeaveRequestCreateRequestDTO;
import com.DATN.DTO.responseDTO.LeaveRequestListResponseDTO;
import com.DATN.DTO.responseDTO.LeaveRequestResponseDTO;
import com.DATN.services.LeaveRequestService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/leave-requests")
@RequiredArgsConstructor
public class LeaveRequestController {
    private final LeaveRequestService leaveRequestService;

    @PostMapping
    public ResponseEntity<LeaveRequestResponseDTO>
            createLeaveRequest(
            @RequestBody
            LeaveRequestCreateRequestDTO request,
            HttpServletRequest httpRequest) {

        String token =
                httpRequest
                        .getHeader("Authorization")
                        .substring(7);

        return ResponseEntity.ok(
                leaveRequestService
                        .createLeaveRequest(
                                request,
                                token));
    }

    @GetMapping("/my-requests")
    public ResponseEntity<
            List<LeaveRequestListResponseDTO>>
            getMyRequests(
            HttpServletRequest request) {

        String token =
                request
                        .getHeader("Authorization")
                        .substring(7);

        return ResponseEntity.ok(
                leaveRequestService
                        .getMyRequests(
                                token));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<LeaveRequestResponseDTO>
            cancelRequest(
            @PathVariable Integer id,
            HttpServletRequest request) {

        String token =
                request
                        .getHeader("Authorization")
                        .substring(7);

        return ResponseEntity.ok(
                leaveRequestService
                        .cancelRequest(
                                id,
                                token));
    }

    @GetMapping("/pending")
    @PreAuthorize("hasAnyRole('HR')")
    public ResponseEntity<
            List<LeaveRequestResponseDTO>>
            getPendingRequests() {

        return ResponseEntity.ok(
                leaveRequestService
                        .getPendingRequests());
    }

    @PutMapping("/{id}/approve")
    @PreAuthorize("hasAnyRole('HR')")
    public ResponseEntity<LeaveRequestResponseDTO>
            approveLeaveRequest(
            @PathVariable Integer id,
            HttpServletRequest request) {

        String token =
                request
                        .getHeader("Authorization")
                        .substring(7);

        Integer managerAccountId =
                leaveRequestService
                        .extractAccountId(token);

        return ResponseEntity.ok(
                leaveRequestService
                        .approveLeaveRequest(
                                id,
                                managerAccountId));
    }

    @PutMapping("/{id}/reject")
    @PreAuthorize("hasAnyRole('HR')")
    public ResponseEntity<LeaveRequestResponseDTO>
            rejectLeaveRequest(
            @PathVariable Integer id,
            HttpServletRequest request) {

        String token =
                request
                        .getHeader("Authorization")
                        .substring(7);

        Integer managerAccountId =
                leaveRequestService
                        .extractAccountId(token);

        return ResponseEntity.ok(
                leaveRequestService
                        .rejectLeaveRequest(
                                id,
                                managerAccountId));
    }
    
}
