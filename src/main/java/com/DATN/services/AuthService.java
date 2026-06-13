package com.DATN.services;

import com.DATN.DTO.requestDTO.LoginRequestDTO;
import com.DATN.DTO.responseDTO.AuthResponseDTO;
import com.DATN.DTO.responseDTO.LogoutResponseDTO;

public interface AuthService {
    AuthResponseDTO login(LoginRequestDTO request);

    LogoutResponseDTO logout();
}
