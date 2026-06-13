package com.DATN.DTO.responseDTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DepartmentResponseDTO {
    private Integer id;

    private String name;
    
    private Integer managerId;

    private String description;
}
