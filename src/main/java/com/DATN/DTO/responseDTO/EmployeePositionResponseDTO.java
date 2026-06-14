package com.DATN.DTO.responseDTO;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeePositionResponseDTO {
    private Integer employeeId;

    private String employeeName;

    private Integer positionId;

    private String positionTitle;

    private Integer departmentId;

    private String departmentName;

    private LocalDate startDate;

    private LocalDate endDate;

    private Boolean active;
}
