package com.DATN.DTO.responseDTO;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeaveRequestListResponseDTO {
    private Integer id;

    private String leaveType;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String status;
}
