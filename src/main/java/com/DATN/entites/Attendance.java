package com.DATN.entites;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
        name = "attendances",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"employee_id", "date"})}
)
@Getter
@Setter
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(
            name = "employee_id",
            nullable = false
    )
    private Employee employee;

    private LocalDate date;

    @Column(name = "check_in")
    private LocalTime checkIn;

    @Column(name = "check_out")
    private LocalTime checkOut;

    @Column(name = "overtime_hours")
    private BigDecimal overtimeHours;

    private String status;
}
