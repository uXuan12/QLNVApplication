package com.DATN.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DATN.DTO.requestDTO.ContractRequestDTO;
import com.DATN.DTO.responseDTO.ContractResponseDTO;
import com.DATN.services.ContractService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/contracts")
@RequiredArgsConstructor
public class ContractController {
    private final ContractService contractService;

    @PostMapping
    @PreAuthorize("hasAnyRole('HR')")
    public ResponseEntity<ContractResponseDTO>
            createContract(
                    @RequestBody
                    ContractRequestDTO request) {

        return ResponseEntity.ok(
                contractService.createContract(
                        request));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('HR')")
    public ResponseEntity<List<ContractResponseDTO>>
            getAllContracts() {

        return ResponseEntity.ok(
                contractService.getAllContracts());
    }

    @PutMapping("/{id}/extend")
    @PreAuthorize("hasAnyRole('HR')")
    public ResponseEntity<ContractResponseDTO>
            extendContract(
                    @PathVariable Integer id,
                    @RequestBody
                    ContractRequestDTO request) {

        return ResponseEntity.ok(
                contractService.extendContract(
                        id,
                        request));
    }

    @PutMapping("/{id}/cancel")
    @PreAuthorize("hasAnyRole('HR')")
    public ResponseEntity<String>
            cancelContract(
                    @PathVariable Integer id) {

        contractService.cancelContract(id);

        return ResponseEntity.ok(
                "Hủy hợp đồng thành công");
    }
}
