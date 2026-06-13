package com.DATN.DTO.responseDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceHistoryResponseDTO {
    private LocalDate date;

    private LocalTime checkIn;

    private LocalTime checkOut;

    private String status;
}
