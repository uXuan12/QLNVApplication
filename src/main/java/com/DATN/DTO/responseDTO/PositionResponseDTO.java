package com.DATN.DTO.responseDTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PositionResponseDTO {
    private Integer id;

    private Integer departmentId;

    private String departmentName;

    private String title;

    private String description;
}
