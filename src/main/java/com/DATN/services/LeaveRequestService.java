package com.DATN.services;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.DATN.DTO.requestDTO.LeaveRequestResponseDTO;
import com.DATN.entites.Employee;
import com.DATN.entites.LeaveRequest;
import com.DATN.repositories.EmployeeRepository;
import com.DATN.repositories.LeaveRequestRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LeaveRequestService {
    private final LeaveRequestRepository leaveRequestRepository;
    private final EmployeeRepository employeeRepository;

    @Transactional
    public LeaveRequestResponseDTO approveLeaveRequest(
            Integer leaveRequestId,
            Integer managerAccountId) {

        
        LeaveRequest leaveRequest =
                leaveRequestRepository.findById(leaveRequestId)
                        .orElseThrow(() ->
                                new RuntimeException("Không tìm thấy đơn"));

        Employee manager =
        employeeRepository
                .findByAccountId(managerAccountId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Không tìm thấy nhân viên"));
                                
        if (!"PENDING".equalsIgnoreCase(
                leaveRequest.getStatus())) {

            throw new RuntimeException(
                    "Chỉ được duyệt đơn đang Pending");
        }

        leaveRequest.setStatus("APPROVED");

        leaveRequest.setApprovedBy(manager.getId());

        leaveRequest.setApprovalDate(
                LocalDateTime.now());

        LeaveRequest saved =
                leaveRequestRepository.save(
                        leaveRequest);

        return toResponseDTO(saved);
    }

    private LeaveRequestResponseDTO
            toResponseDTO(LeaveRequest leaveRequest) {

        LeaveRequestResponseDTO dto =
                new LeaveRequestResponseDTO();

        dto.setId(leaveRequest.getId());

        dto.setEmployeeId(
                leaveRequest.getEmployee().getId());

        dto.setLeaveType(
                leaveRequest.getLeaveType());

        dto.setStartTime(
                leaveRequest.getStartTime());

        dto.setEndTime(
                leaveRequest.getEndTime());

        dto.setReason(
                leaveRequest.getReason());

        dto.setApprovedBy(
                leaveRequest.getApprovedBy());

        dto.setApprovalDate(
                leaveRequest.getApprovalDate());

        dto.setStatus(
                leaveRequest.getStatus());

        return dto;
    }
}
