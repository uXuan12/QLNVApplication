package com.DATN.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.DATN.DTO.requestDTO.LeaveRequestCreateRequestDTO;
import com.DATN.DTO.responseDTO.LeaveRequestListResponseDTO;
import com.DATN.DTO.responseDTO.LeaveRequestResponseDTO;
import com.DATN.entites.Employee;
import com.DATN.entites.LeaveRequest;
import com.DATN.repositories.EmployeeRepository;
import com.DATN.repositories.LeaveRequestRepository;
import com.DATN.security.util.JwtUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LeaveRequestService {
    private final LeaveRequestRepository leaveRequestRepository;

    private final EmployeeRepository employeeRepository;

    private final JwtUtil jwtUtil;

    public Integer extractAccountId(String token) {

        return jwtUtil.extractAccountId(token);
    }

    //nhân viên tạo đơn
    @Transactional
    public LeaveRequestResponseDTO createLeaveRequest(
            LeaveRequestCreateRequestDTO request,
            String token) {

        Integer accountId =
                jwtUtil.extractAccountId(token);

        Employee employee =
                employeeRepository
                        .findByAccountId(accountId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Không tìm thấy nhân viên"));

        if (request.getStartTime()
                .isAfter(request.getEndTime())) {

            throw new RuntimeException(
                    "Ngày kết thúc phải lớn hơn ngày bắt đầu");
        }

        if (request.getStartTime()
                .isBefore(LocalDateTime.now())) {

            throw new RuntimeException(
                    "Thời gian nghỉ phải lớn hơn hiện tại");
        }

        if (!request.getLeaveType()
                .equals("ANNUAL_LEAVE")
                && !request.getLeaveType()
                        .equals("SICK_LEAVE")
                && !request.getLeaveType()
                        .equals("UNPAID_LEAVE")) {

            throw new RuntimeException(
                    "Loại đơn không hợp lệ");
        }

        LeaveRequest leaveRequest =
                new LeaveRequest();

        leaveRequest.setEmployee(employee);

        leaveRequest.setLeaveType(
                request.getLeaveType());

        leaveRequest.setStartTime(
                request.getStartTime());

        leaveRequest.setEndTime(
                request.getEndTime());

        leaveRequest.setReason(
                request.getReason());

        leaveRequest.setStatus(
                "PENDING");

        LeaveRequest saved =
                leaveRequestRepository
                        .save(leaveRequest);

        return toResponseDTO(saved);
    }

    //xem danh sách đơn đã gửi
    public List<LeaveRequestListResponseDTO>
            getMyRequests(
            String token) {

        Integer accountId =
                jwtUtil.extractAccountId(token);

        Employee employee =
                employeeRepository
                        .findByAccountId(accountId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Không tìm thấy nhân viên"));

        return leaveRequestRepository
                .findByEmployeeOrderByIdDesc(
                        employee)
                .stream()
                .map(this::toListResponseDTO)
                .toList();
    }

    //nhân viên hủy đơn khi chưa được duyệt
    @Transactional
    public LeaveRequestResponseDTO cancelRequest(
            Integer leaveRequestId,
            String token) {

        Integer accountId =
                jwtUtil.extractAccountId(token);

        Employee employee =
                employeeRepository
                        .findByAccountId(accountId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Không tìm thấy nhân viên"));

        LeaveRequest leaveRequest =
                leaveRequestRepository
                        .findById(leaveRequestId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Không tìm thấy đơn"));

        if (!leaveRequest
                .getEmployee()
                .getId()
                .equals(employee.getId())) {

            throw new RuntimeException(
                    "Bạn không được phép hủy đơn này");
        }

        if (!"PENDING".equalsIgnoreCase(
                leaveRequest.getStatus())) {

            throw new RuntimeException(
                    "Chỉ được hủy đơn đang chờ duyệt");
        }

        leaveRequest.setStatus(
                "CANCELLED");

        LeaveRequest saved =
                leaveRequestRepository
                        .save(leaveRequest);

        return toResponseDTO(saved);
    }

    // danh sách đơn đang chờ duyệt
    public List<LeaveRequestResponseDTO>
            getPendingRequests() {

        return leaveRequestRepository
                .findByStatusOrderByIdDesc(
                        "PENDING")
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // duyệt đơn
    @Transactional
    public LeaveRequestResponseDTO approveLeaveRequest(
            Integer leaveRequestId,
            Integer managerAccountId) {

        LeaveRequest leaveRequest =
                leaveRequestRepository
                        .findById(leaveRequestId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Không tìm thấy đơn"));

        Employee manager =
                employeeRepository
                        .findByAccountId(
                                managerAccountId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Không tìm thấy nhân viên"));

        if (!"PENDING".equalsIgnoreCase(
                leaveRequest.getStatus())) {

            throw new RuntimeException(
                    "Chỉ được duyệt đơn đang Pending");
        }

        leaveRequest.setStatus(
                "APPROVED");

        leaveRequest.setApprovedBy(
                manager.getId());

        leaveRequest.setApprovalDate(
                LocalDateTime.now());

        LeaveRequest saved =
                leaveRequestRepository
                        .save(leaveRequest);

        return toResponseDTO(saved);
    }

    // từ chối đơn
    @Transactional
    public LeaveRequestResponseDTO rejectLeaveRequest(
            Integer leaveRequestId,
            Integer managerAccountId) {

        LeaveRequest leaveRequest =
                leaveRequestRepository
                        .findById(leaveRequestId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Không tìm thấy đơn"));

        Employee manager =
                employeeRepository
                        .findByAccountId(
                                managerAccountId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Không tìm thấy nhân viên"));

        if (!"PENDING".equalsIgnoreCase(
                leaveRequest.getStatus())) {

            throw new RuntimeException(
                    "Chỉ được từ chối đơn đang Pending");
        }

        leaveRequest.setStatus(
                "REJECTED");

        leaveRequest.setApprovedBy(
                manager.getId());

        leaveRequest.setApprovalDate(
                LocalDateTime.now());

        LeaveRequest saved =
                leaveRequestRepository
                        .save(leaveRequest);

        return toResponseDTO(saved);
    }

    private LeaveRequestResponseDTO
            toResponseDTO(
            LeaveRequest leaveRequest) {

        LeaveRequestResponseDTO dto =
                new LeaveRequestResponseDTO();

        dto.setId(
                leaveRequest.getId());

        dto.setEmployeeId(
                leaveRequest.getEmployee()
                        .getId());

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

    private LeaveRequestListResponseDTO
            toListResponseDTO(
            LeaveRequest leaveRequest) {

        LeaveRequestListResponseDTO dto =
                new LeaveRequestListResponseDTO();

        dto.setId(
                leaveRequest.getId());

        dto.setLeaveType(
                leaveRequest.getLeaveType());

        dto.setStartTime(
                leaveRequest.getStartTime());

        dto.setEndTime(
                leaveRequest.getEndTime());

        dto.setStatus(
                leaveRequest.getStatus());

        return dto;
    }
}
