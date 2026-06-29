package com.DATN.DTO.responseDTO;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalaryResponseDTO {

    private Integer id;

    private Integer employeeId;
    
    private String employeeName;

    private Integer accountId;

    private Integer salaryMonth;

    private Integer salaryYear;

    private Integer basicSalary;

    private Double allowance;

    private Double bonus;

    private Double deduction;

    private Double netSalary;

    private LocalDate paymentDate;

    private String status;
}
