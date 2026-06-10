package com.DATN.DTO.responseDTO;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalaryResponseDTO {

    private Integer id;

    private Integer employeeId;
    
    private Integer salaryMonth;

    private Integer salaryYear;

    private Double basicSalary;

    private Double allowance;

    private Double bonus;

    private Double deduction;

    private Double netSalary;

    private LocalDate paymentDate;

    private String status;
}
