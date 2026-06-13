package com.DATN.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.DATN.DTO.requestDTO.LoginRequestDTO;
import com.DATN.DTO.responseDTO.AuthResponseDTO;
import com.DATN.DTO.responseDTO.LogoutResponseDTO;
import com.DATN.entites.Account;
import com.DATN.repositories.AccountRepository;
import com.DATN.security.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    @Override
    public AuthResponseDTO login(LoginRequestDTO request) {

        Account account = accountRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() ->
                        new RuntimeException("Sai tên đăng nhập "));

        if (!account.getStatus().equals("ACTIVE")) {
            throw new RuntimeException("Tài khoản đã bị khóa");
        }

        if (!passwordEncoder.matches(
                request.getPassword(),
                account.getPassword())) {

            throw new RuntimeException("Sai mật khẩu");
        }

        String role = account.getRole().getName();

        String token =
                jwtUtil.generateToken(
                        account.getId(),
                        account.getUsername(),
                        role
                );

        return new AuthResponseDTO(
                account.getId(),
                account.getUsername(),
                role,
                token
        );
    }

    @Override
    public LogoutResponseDTO logout() {
        return new LogoutResponseDTO("Đăng xuất thành công");
    }
}
