package com.DATN.entites;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "salaries")
@Getter
@Setter
public class Salary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @NotNull
    @Column(name = "salary_month")
    private Integer salaryMonth;

    @NotNull
    @Column(name = "salary_year")
    private Integer salaryYear;

    @NotNull
    @Positive
    @Column(name = "basic_salary")
    private Double basicSalary;

    @PositiveOrZero
    private Double allowance;

    @PositiveOrZero
    private Double bonus;

    @PositiveOrZero
    private Double deduction;

    @PositiveOrZero
    @Column(name = "net_salary")
    private Double netSalary;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    private String status;
}
