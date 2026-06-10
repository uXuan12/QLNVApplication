package com.DATN.DTO.responseDTO;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceResponseDTO {
    private Integer id;

    private LocalDate date;

    private LocalTime checkIn;

    private String status;
}
