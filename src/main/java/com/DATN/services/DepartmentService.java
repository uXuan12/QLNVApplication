package com.DATN.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.DATN.DTO.requestDTO.DepartmentRequestDTO;
import com.DATN.DTO.responseDTO.DepartmentResponseDTO;
import com.DATN.entites.Department;
import com.DATN.entites.Employee;
import com.DATN.entites.EmployeePosition;
import com.DATN.entites.Position;
import com.DATN.repositories.DepartmentRepository;
import com.DATN.repositories.EmployeePositionRepository;
import com.DATN.repositories.EmployeeRepository;
import com.DATN.repositories.PositionRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final EmployeePositionRepository employeePositionRepository;
    private final PositionRepository positionRepository;

    public List<DepartmentResponseDTO> getAllDepartments() {

        List<Department> departments =
                departmentRepository.findAll();

        return departments.stream()
                .map(this::toResponseDTO)
                .toList();
    }

    //Tạo phòng ban
    @Transactional
    public DepartmentResponseDTO createDepartment(
            DepartmentRequestDTO request) {

        validateDepartmentName(
                request.getName());

        String departmentName =
                request.getName().trim();

        if (departmentRepository
                .existsByName(departmentName)) {

            throw new RuntimeException(
                    "Phòng ban đã tồn tại");
        }

        Department department =
                new Department();

        department.setName(
                departmentName);

        department.setDescription(
                request.getDescription());

        Department savedDepartment =
                departmentRepository.save(
                        department);

        //Validate manager sau khi có id phòng ban
        if (request.getManagerId() != null) {

            validateManager(
                    request.getManagerId(),
                    savedDepartment.getId());

            savedDepartment.setManagerId(
                    request.getManagerId());

            savedDepartment =
                    departmentRepository.save(
                            savedDepartment);
        }

        return toResponseDTO(
                savedDepartment);
    }

    //Cập nhật phòng ban
    @Transactional
    public DepartmentResponseDTO updateDepartment(
            Integer departmentId,
            DepartmentRequestDTO request) {

        Department department =
                departmentRepository
                        .findById(departmentId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Không tìm thấy phòng ban"));

        validateDepartmentName(
                request.getName());

        String departmentName =
                request.getName().trim();

        Department existedDepartment =
                departmentRepository
                        .findByName(departmentName)
                        .orElse(null);

        if (existedDepartment != null
                && !existedDepartment.getId()
                        .equals(departmentId)) {

            throw new RuntimeException(
                    "Phòng ban đã tồn tại");
        }

        if (request.getManagerId() != null) {

            validateManager(
                    request.getManagerId(),
                    departmentId);
        }

        department.setName(
                departmentName);

        department.setManagerId(
                request.getManagerId());

        department.setDescription(
                request.getDescription());

        Department updatedDepartment =
                departmentRepository.save(
                        department);

        return toResponseDTO(
                updatedDepartment);
    }

    //Xóa phòng ban
    @Transactional
    public void deleteDepartment(
            Integer departmentId) {

        Department department =
                departmentRepository
                        .findById(departmentId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Không tìm thấy phòng ban"));

        if (positionRepository
                .existsByDepartmentId(
                        departmentId)) {

            throw new RuntimeException(
                    "Không thể xóa phòng ban đang chứa chức vụ");
        }

        departmentRepository.delete(
                department);
    }

    private void validateDepartmentName(
            String departmentName) {

        if (departmentName == null
                || departmentName.isBlank()) {

            throw new RuntimeException(
                    "Tên phòng ban không được để trống");
        }
    }
 
    private void validateManager(
            Integer managerId,
            Integer departmentId) {

        Employee employee =
                employeeRepository
                        .findById(managerId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Không tìm thấy nhân viên"));

        EmployeePosition employeePosition =
                employeePositionRepository
                        .findByEmployeeAndIsActiveTrue(
                                employee)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Nhân viên chưa được phân công vị trí"));

        Position position =
                employeePosition.getPosition();

        if (position == null
                || position.getDepartment() == null) {

            throw new RuntimeException(
                    "Vị trí chưa thuộc phòng ban nào");
        }

        Integer positionDepartmentId =
                position.getDepartment()
                        .getId();

        if (!positionDepartmentId.equals(
                departmentId)) {

            throw new RuntimeException(
                    "Trưởng phòng phải thuộc chính phòng ban này");
        }
    }

    private DepartmentResponseDTO toResponseDTO(
            Department department) {

        DepartmentResponseDTO dto =
                new DepartmentResponseDTO();

        dto.setId(
                department.getId());

        dto.setName(
                department.getName());

        dto.setManagerId(
                department.getManagerId());

        dto.setDescription(
                department.getDescription());

        return dto;
    }
        
}
