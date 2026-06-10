package com.DATN.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DATN.entites.Account;
import com.DATN.repositories.AccountRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountRepository accountRepository;

    @GetMapping
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
}
