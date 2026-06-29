package com.DATN.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.DATN.DTO.requestDTO.SalaryCalculationRequestDTO;
import com.DATN.DTO.responseDTO.SalaryCalculationResponseDTO;
import com.DATN.DTO.responseDTO.SalaryListResponseDTO;
import com.DATN.DTO.responseDTO.SalaryResponseDTO;
import com.DATN.entites.Contract;
import com.DATN.entites.Employee;
import com.DATN.entites.Salary;
import com.DATN.repositories.AttendanceRepository;
import com.DATN.repositories.ContractRepository;
import com.DATN.repositories.EmployeeRepository;
import com.DATN.repositories.SalaryRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
@Transactional
public class SalaryService {
    private final EmployeeRepository employeeRepository;

    private final SalaryRepository salaryRepository;
    private final AttendanceRepository attendanceRepository;
    private final ContractRepository contractRepository;

    public List<SalaryListResponseDTO>
            getAllSalaries() {

        return salaryRepository
                .findAllByOrderBySalaryYearDescSalaryMonthDesc()
                .stream()
                .map(this::toListDTO)
                .toList();
    }

    public SalaryResponseDTO getMyPayslip(
            Integer accountId) {

        Employee employee =
                employeeRepository
                        .findByAccountId(accountId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Không tìm thấy nhân viên"));

        
        Salary salary =
                salaryRepository
                        .findTopByEmployee_IdOrderBySalaryYearDescSalaryMonthDesc(
                                employee.getId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Chưa có phiếu lương"));

        return mapToResponseDTO(salary);
    }

    private SalaryListResponseDTO
            toListDTO(Salary salary) {

        SalaryListResponseDTO dto =
                new SalaryListResponseDTO();

        dto.setId(salary.getId());

        dto.setEmployeeId(
                salary.getEmployee().getId());

        dto.setEmployeeName(
                salary.getEmployee().getFullName());

        dto.setSalaryMonth(
                salary.getSalaryMonth());

        dto.setSalaryYear(
                salary.getSalaryYear());

        dto.setNetSalary(
                salary.getNetSalary());

        dto.setPaymentDate(
                salary.getPaymentDate());

        dto.setStatus(
                salary.getStatus());

        return dto;
    }
    
    private SalaryResponseDTO mapToResponseDTO(
            Salary salary) {

        SalaryResponseDTO dto =
                new SalaryResponseDTO();

        dto.setId(salary.getId());

        dto.setEmployeeId(
                salary.getEmployee().getId());
        
        dto.setEmployeeName(salary.getEmployee().getFullName());

        dto.setAccountId(salary.getEmployee().getAccount().getId());

        dto.setSalaryMonth(
                salary.getSalaryMonth());

        dto.setSalaryYear(
                salary.getSalaryYear());

        dto.setBasicSalary(
                salary.getBasicSalary());

        dto.setAllowance(
                salary.getAllowance());

        dto.setBonus(
                salary.getBonus());

        dto.setDeduction(
                salary.getDeduction());

        dto.setNetSalary(
                salary.getNetSalary());

        dto.setPaymentDate(
                salary.getPaymentDate());

        dto.setStatus(
                salary.getStatus());

        return dto;
    }

    // tính lương
    public SalaryCalculationResponseDTO calculateSalary(
        SalaryCalculationRequestDTO request) {

    Employee employee =
            employeeRepository.findById(
                    request.getEmployeeId())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Không tìm thấy nhân viên"));

    if (salaryRepository
            .existsByEmployee_IdAndSalaryMonthAndSalaryYear(
                    employee.getId(),
                    request.getSalaryMonth(),
                    request.getSalaryYear())) {

        throw new RuntimeException(
                "Bảng lương tháng này đã tồn tại");
    }

    Contract contract =
            contractRepository
                    .findByEmployeeIdAndStatus(
                            employee.getId(),
                            "ACTIVE")
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Nhân viên chưa có hợp đồng ACTIVE"));

    LocalDate startDate =
            LocalDate.of(
                    request.getSalaryYear(),
                    request.getSalaryMonth(),
                    1);

    LocalDate endDate =
            startDate.withDayOfMonth(
                    startDate.lengthOfMonth());

    int workingDays =
            (int) attendanceRepository
                    .countByEmployeeAndStatusInAndDateBetween(
                            employee,
                            List.of("VALID","LATE"),
                            startDate,
                            endDate);

    int basicSalary =
            contract.getSalary();

    double allowance =
            request.getAllowance() == null
                    ? 0
                    : request.getAllowance();

    double bonus =
            request.getBonus() == null
                    ? 0
                    : request.getBonus();

    double deduction =
            request.getDeduction() == null
                    ? 0
                    : request.getDeduction();

    double netSalary =
            ((basicSalary
                    + allowance
                    + bonus
                    - deduction)
                    * workingDays)
                    / 30.0;

    Salary salary =
            new Salary();

    salary.setEmployee(employee);

    salary.setSalaryMonth(
            request.getSalaryMonth());

    salary.setSalaryYear(
            request.getSalaryYear());

    salary.setBasicSalary(
            basicSalary);

    salary.setAllowance(
            allowance);

    salary.setBonus(
            bonus);

    salary.setDeduction(
            deduction);

    salary.setNetSalary(
            netSalary);

    salary.setPaymentDate(
            LocalDate.now());

    salary.setStatus("PAID");

    salaryRepository.save(salary);

    SalaryCalculationResponseDTO dto =
            new SalaryCalculationResponseDTO();

    dto.setEmployeeId(employee.getId());
    dto.setEmployeeName(employee.getFullName());
    dto.setWorkingDays(workingDays);
    dto.setBasicSalary(basicSalary);
    dto.setAllowance(allowance);
    dto.setBonus(bonus);
    dto.setDeduction(deduction);
    dto.setNetSalary(netSalary);

    return dto;
    }
}
