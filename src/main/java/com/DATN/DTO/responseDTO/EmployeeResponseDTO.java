package com.DATN.DTO.responseDTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponseDTO {
    private Integer id;

    private String fullName;

    private String email;

    private String phone;

    private LocalDate dob;

    private String gender;

    private String address;

    private String status;
}
