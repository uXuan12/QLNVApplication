package com.DATN.DTO.requestDTO;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeRequestDTO {
    @NotNull
    private Integer accountId;

    @NotBlank(message = "Họ tên không được để trống")
    private String fullName;

    @NotBlank(message = "Email không được để trống")
    private String email;

    @NotBlank(message = "Số điện thoại không được để trống")
    private String phone;

    @NotNull(message = "Ngày sinh không được để trống")
    private LocalDate dob;

    private String gender;

    private String address;

    private String status;
}
