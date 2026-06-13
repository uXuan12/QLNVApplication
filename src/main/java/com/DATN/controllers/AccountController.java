package com.DATN.controllers;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DATN.DTO.requestDTO.AccountRequestDTO;
import com.DATN.DTO.requestDTO.AccountUpdateRequestDTO;
import com.DATN.DTO.responseDTO.AccountResponseDTO;
import com.DATN.services.AccountService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<AccountResponseDTO>
            getAllAccounts() {

        return accountService.getAllAccounts();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public AccountResponseDTO createAccount(
            @RequestBody
            AccountRequestDTO request) {

        return accountService.createAccount(
                request);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public AccountResponseDTO updateAccount(
            @PathVariable Integer id,
            @RequestBody AccountUpdateRequestDTO request) {

        return accountService.updateAccount(
                id,
                request);
    }
    
}
