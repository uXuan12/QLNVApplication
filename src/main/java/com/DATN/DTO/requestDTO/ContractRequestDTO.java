package com.DATN.DTO.requestDTO;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContractRequestDTO {
    private Integer employeeId;

    private String contractType;

    private Integer salary;

    private LocalDate startDate;

    private LocalDate endDate;
}
