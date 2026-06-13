package com.DATN.DTO.responseDTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AccountResponseDTO {
    private Integer id;

    private String username;

    private String status;

    private String role;
}
