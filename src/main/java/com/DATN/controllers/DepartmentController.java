package com.DATN.controllers;

import java.util.List;

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

import com.DATN.DTO.requestDTO.DepartmentRequestDTO;
import com.DATN.DTO.responseDTO.DepartmentResponseDTO;
import com.DATN.services.DepartmentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;

    @GetMapping
    public List<DepartmentResponseDTO>
            getAllDepartments() {

        return departmentService.getAllDepartments();
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public ResponseEntity<DepartmentResponseDTO>
            createDepartment(
                    @RequestBody DepartmentRequestDTO request) {

        DepartmentResponseDTO department =
                departmentService.createDepartment(
                        request);

        return ResponseEntity.ok(department);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public ResponseEntity<DepartmentResponseDTO>
            updateDepartment(
                    @PathVariable Integer id,
                    @RequestBody DepartmentRequestDTO request) {

        DepartmentResponseDTO response =
                departmentService
                        .updateDepartment(
                                id,
                                request);

        return ResponseEntity.ok(
                response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public ResponseEntity<String>
            deleteDepartment(
                    @PathVariable Integer id) {

        departmentService.deleteDepartment(id);

        return ResponseEntity.ok(
                "Xóa phòng ban thành công");
    }
}
