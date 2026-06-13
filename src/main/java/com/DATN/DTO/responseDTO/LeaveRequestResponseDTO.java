package com.DATN.DTO.responseDTO;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeaveRequestResponseDTO {
    private Integer id;

    private Integer employeeId;

    private String leaveType;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String reason;

    private Integer approvedBy;

    private LocalDateTime approvalDate;

    private String status;
}
