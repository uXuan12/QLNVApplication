package com.DATN.DTO.responseDTO;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContractResponseDTO {
    private Integer id;

    private Integer employeeId;

    private String employeeName;

    private String contractType;

    private Integer salary;

    private LocalDate startDate;

    private LocalDate endDate;

    private String status;
}
