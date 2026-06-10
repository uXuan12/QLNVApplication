package com.DATN.DTO.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDTO {
    private Integer accountId;

    private String username;

    private String role;

    private String token;
}
