package com.DATN.DTO.responseDTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeListResponseDTO {
    private Integer id;

    private String fullName;

    private String email;

    private String phone;

    private LocalDate dob;

    private String gender;

    private String address;

    private String status;

    private String username;

    private String role;

    private String department;

    private String position;
}
