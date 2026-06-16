package com.DATN.DTO.requestDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalaryCalculationRequestDTO {
    
    private Integer employeeId; 

    private Integer salaryMonth; 

    private Integer salaryYear; 

    private Double allowance; 

    private Double bonus;

    private Double deduction;
}
