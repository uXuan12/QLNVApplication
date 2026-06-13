package com.DATN.DTO.requestDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountRequestDTO {
    private String username;

    private String password;

    private Integer roleId;
}
