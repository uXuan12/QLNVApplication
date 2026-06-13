package com.DATN.DTO.responseDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttendanceRateResponseDTO {
    private Long totalAttendance;

    private Long validAttendance;

    private Long lateAttendance;

    private Double attendanceRate;
}
