package com.DATN.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DATN.DTO.requestDTO.PositionRequest;
import com.DATN.DTO.responseDTO.PositionResponseDTO;
import com.DATN.services.PositionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/positions")
@RequiredArgsConstructor
public class PositionController {
    private final PositionService positionService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public List<PositionResponseDTO>
            getAllPositions() {

        return positionService.getAllPositions();
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<PositionResponseDTO>
    createPosition(
            @RequestBody PositionRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(positionService
                        .createPosition(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<PositionResponseDTO>
            updatePosition(
                    @PathVariable Integer id,
                    @RequestBody PositionRequest request) {

        PositionResponseDTO response =
                positionService
                        .updatePosition(
                                id,
                                request);

        return ResponseEntity.ok(
                response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<String>
            deletePosition(
                    @PathVariable Integer id) {

        positionService.deletePosition(id);

        return ResponseEntity.ok(
                "Xóa chức vụ thành công");
    }
}
