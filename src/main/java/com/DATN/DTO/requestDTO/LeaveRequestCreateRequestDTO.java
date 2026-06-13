package com.DATN.DTO.requestDTO;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeaveRequestCreateRequestDTO {
    private String leaveType;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String reason;
}
