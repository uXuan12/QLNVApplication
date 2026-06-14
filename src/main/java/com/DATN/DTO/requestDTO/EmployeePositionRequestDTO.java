package com.DATN.DTO.requestDTO;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeePositionRequestDTO {
        private Integer employeeId;

    private Integer positionId;

    private LocalDate startDate;

}
