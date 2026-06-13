package com.DATN.DTO.requestDTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DepartmentRequestDTO {
    private String name;

    private Integer managerId;
    
    private String description;
}
