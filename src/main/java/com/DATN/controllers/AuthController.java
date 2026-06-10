package com.DATN.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DATN.DTO.requestDTO.LoginRequestDTO;
import com.DATN.DTO.responseDTO.AuthResponseDTO;
import com.DATN.services.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO request) {

        return ResponseEntity.ok(
                authService.login(request)
        );
    }
}
