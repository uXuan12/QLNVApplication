package com.DATN.DTO.requestDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountUpdateRequestDTO {
    private String username;

    private String password;

    private Integer roleId;

    private String status;
}
