package com.DATN.entites;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "leave_requests")
@Getter
@Setter
public class LeaveRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "leave_type")
    private String leaveType;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    private String reason;

    @Column(name = "approved_by")
    private Integer approvedBy;

    @Column(name = "approval_date")
    private LocalDateTime approvalDate;

    private String status;
}
