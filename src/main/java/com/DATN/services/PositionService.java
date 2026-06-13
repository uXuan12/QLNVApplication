package com.DATN.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.DATN.DTO.requestDTO.PositionRequest;
import com.DATN.DTO.responseDTO.PositionResponseDTO;
import com.DATN.entites.Department;
import com.DATN.entites.Position;
import com.DATN.repositories.DepartmentRepository;
import com.DATN.repositories.EmployeePositionRepository;
import com.DATN.repositories.PositionRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PositionService {
    private final PositionRepository positionRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeePositionRepository employeePositionRepository;

    public List<PositionResponseDTO> getAllPositions() {

        List<Position> positions =
                positionRepository.findAll();

        return positions.stream()
                .map(this::toResponseDTO)
                .toList();
    }
    
    @Transactional
    public PositionResponseDTO createPosition(
            PositionRequest request) {

        if (request.getTitle() == null
                || request.getTitle().isBlank()) {

            throw new RuntimeException(
                    "Tên chức vụ không được để trống");
        }

        Department department =
                departmentRepository
                        .findById(request.getDepartmentId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Phòng ban không tồn tại"));

        if (positionRepository.existsByDepartmentIdAndTitle(
                request.getDepartmentId(),
                request.getTitle())) {

            throw new RuntimeException(
                    "Chức vụ đã tồn tại");
        }

        Position position = new Position();

        position.setDepartment(department);
        position.setTitle(request.getTitle());
        position.setDescription(
                request.getDescription());

        Position savedPosition =
                positionRepository.save(position);

        PositionResponseDTO response =
                new PositionResponseDTO();

        response.setId(savedPosition.getId());

        response.setDepartmentId(
                savedPosition.getDepartment().getId());

        response.setDepartmentName(
                savedPosition.getDepartment().getName());

        response.setTitle(
                savedPosition.getTitle());

        response.setDescription(
                savedPosition.getDescription());

        return response;
    }

    @Transactional
    public PositionResponseDTO updatePosition(
            Integer positionId,
            PositionRequest request) {

        Position position =
                positionRepository
                        .findById(positionId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Không tìm thấy chức vụ"));

        if (request.getTitle() == null
                || request.getTitle().isBlank()) {

            throw new RuntimeException(
                    "Tên chức vụ không được để trống");
        }

        Department department =
                departmentRepository
                        .findById(
                                request.getDepartmentId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Phòng ban không tồn tại"));

        String newTitle =
                request.getTitle().trim();

        Position existedPosition =
                positionRepository
                        .findByDepartmentIdAndTitle(
                                department.getId(),
                                newTitle)
                        .orElse(null);

        if (existedPosition != null
                && !existedPosition.getId()
                        .equals(positionId)) {

            throw new RuntimeException(
                    "Chức vụ đã tồn tại trong phòng ban này");
        }

        position.setDepartment(department);

        position.setTitle(newTitle);

        position.setDescription(
                request.getDescription());

        Position updatedPosition =
                positionRepository.save(
                        position);

        return toResponseDTO(
                updatedPosition);
    }

    @Transactional
    public void deletePosition(
            Integer positionId) {

        Position position =
                positionRepository
                        .findById(positionId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Không tìm thấy chức vụ"));

        if (employeePositionRepository
                .existsByPositionId(positionId)) {

            throw new RuntimeException(
                    "Không thể xóa chức vụ đang được nhân viên sử dụng");
        }

        positionRepository.delete(position);
    }

    private PositionResponseDTO toResponseDTO(
            Position position) {

        PositionResponseDTO dto =
                new PositionResponseDTO();

        dto.setId(position.getId());

        dto.setDepartmentId(
                position.getDepartment().getId());

        dto.setDepartmentName(
                position.getDepartment().getName());

        dto.setTitle(
                position.getTitle());

        dto.setDescription(
                position.getDescription());

        return dto;
    }
}
