package com.DATN.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.DATN.DTO.responseDTO.AttendanceRateResponseDTO;
import com.DATN.DTO.responseDTO.WorkforceChartResponseDTO;
import com.DATN.entites.Department;
import com.DATN.entites.Position;
import com.DATN.repositories.AttendanceRepository;
import com.DATN.repositories.DepartmentRepository;
import com.DATN.repositories.EmployeePositionRepository;
import com.DATN.repositories.PositionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final DepartmentRepository departmentRepository;

    private final PositionRepository positionRepository;

    private final EmployeePositionRepository employeePositionRepository;

    private final AttendanceRepository attendanceRepository;

    public List<WorkforceChartResponseDTO>
            getWorkforceChart() {

        List<WorkforceChartResponseDTO>
                result = new ArrayList<>();

        List<Department>
                departments =
                departmentRepository.findAll();

        for (Department department
                : departments) {

            long employeeCount = 0;

            List<Position> positions =
                    positionRepository.findAll()
                            .stream()
                            .filter(position ->
                                    position
                                            .getDepartment()
                                            .getId()
                                            .equals(
                                                    department.getId()))
                            .toList();

            for (Position position
                    : positions) {

                employeeCount +=
                        employeePositionRepository
                                .countByPositionIdAndIsActiveTrue(
                                        position.getId());
            }

            WorkforceChartResponseDTO dto =
                    new WorkforceChartResponseDTO();

            dto.setDepartmentName(
                    department.getName());

            dto.setEmployeeCount(
                    employeeCount);

            result.add(dto);
        }

        return result;
    }

    public AttendanceRateResponseDTO
            getAttendanceRate() {

        long total =
                attendanceRepository.count();

        long valid =
                attendanceRepository
                        .countByStatus("VALID");

        long late =
                attendanceRepository
                        .countByStatus("LATE");

        double rate = 0;

        if (total > 0) {

            rate =
                    ((double) valid / total)
                            * 100;
        }

        AttendanceRateResponseDTO dto =
                new AttendanceRateResponseDTO();

        dto.setTotalAttendance(
                total);

        dto.setValidAttendance(
                valid);

        dto.setLateAttendance(
                late);

        dto.setAttendanceRate(
                Math.round(rate * 100.0)
                        / 100.0);

        return dto;
    }
}
