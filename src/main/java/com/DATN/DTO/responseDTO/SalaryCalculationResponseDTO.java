package com.DATN.DTO.responseDTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SalaryCalculationResponseDTO {

    private Integer employeeId;

    private String employeeName;

    private Integer workingDays;

    private Integer basicSalary;

    private Double allowance;

    private Double bonus;

    private Double deduction;

    private Double netSalary;

}
