package com.DATN.services;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.DATN.DTO.requestDTO.AccountRequestDTO;
import com.DATN.DTO.requestDTO.AccountUpdateRequestDTO;
import com.DATN.DTO.responseDTO.AccountResponseDTO;
import com.DATN.entites.Account;
import com.DATN.entites.Role;
import com.DATN.repositories.AccountRepository;
import com.DATN.repositories.RoleRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public List<AccountResponseDTO> getAllAccounts() {

        List<Account> accounts =
                accountRepository.findAll();

        return accounts.stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Transactional
    public AccountResponseDTO createAccount(
                AccountRequestDTO request) {

        if (accountRepository.existsByUsername(
                request.getUsername().trim())) {

                throw new RuntimeException(
                        "Tên đăng nhập đã tồn tại");
        }

        Role role =
                roleRepository.findById(
                        request.getRoleId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Không tìm thấy vai trò"));

        if (role.getName()
                .equalsIgnoreCase("ADMIN")) {

                throw new RuntimeException(
                        "Không được tạo thêm tài khoản ADMIN");
        }

        if (request.getPassword() == null
                || request.getPassword().isBlank()) {

                throw new RuntimeException(
                        "Mật khẩu không được để trống");
        }

        Account account =
                new Account();

        account.setUsername(
                request.getUsername().trim());

        account.setPassword(
                passwordEncoder.encode(
                        request.getPassword()));

        account.setRole(
                role);

        account.setStatus(
                "ACTIVE");

        Account saved =
                accountRepository.save(
                        account);

        return toResponseDTO(
                saved);
    }

    @Transactional
    public AccountResponseDTO updateAccount(
            Integer id,
            AccountUpdateRequestDTO request) {

        Account account =
                accountRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Không tìm thấy tài khoản"));

        Account existed =
                accountRepository.findByUsername(
                        request.getUsername())
                        .orElse(null);

        if (existed != null &&
                !existed.getId().equals(id)) {

            throw new RuntimeException(
                    "Tên đăng nhập đã tồn tại");
        }

        Role role =
                roleRepository.findById(
                        request.getRoleId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Không tìm thấy vai trò"));

        if (!request.getStatus()
                .equalsIgnoreCase("ACTIVE")
                &&
                !request.getStatus()
                        .equalsIgnoreCase("INACTIVE")) {

            throw new RuntimeException(
                    "Trạng thái không hợp lệ");
        }

        account.setUsername(
                request.getUsername().trim());

        account.setRole(role);

        account.setStatus(
                request.getStatus().toUpperCase());

        if (request.getPassword() != null
                &&
                !request.getPassword().isBlank()) {

            account.setPassword(
                    passwordEncoder.encode(
                            request.getPassword()));
        }

        Account saved =
                accountRepository.save(account);

        return toResponseDTO(saved);
    }

    private AccountResponseDTO toResponseDTO(
            Account account) {

        AccountResponseDTO dto =
                new AccountResponseDTO();

        dto.setId(account.getId());
        dto.setUsername(account.getUsername());
        dto.setStatus(account.getStatus());
        dto.setRole(account.getRole().getName());

        return dto;
    }
}
