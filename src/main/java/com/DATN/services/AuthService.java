package com.DATN.services;

import com.DATN.DTO.requestDTO.LoginRequestDTO;
import com.DATN.DTO.responseDTO.AuthResponseDTO;

public interface AuthService {
    AuthResponseDTO login(LoginRequestDTO request);
}
