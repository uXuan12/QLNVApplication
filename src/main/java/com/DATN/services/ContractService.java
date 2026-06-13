package com.DATN.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.DATN.DTO.requestDTO.ContractRequestDTO;
import com.DATN.DTO.responseDTO.ContractResponseDTO;
import com.DATN.entites.Contract;
import com.DATN.entites.Employee;
import com.DATN.repositories.ContractRepository;
import com.DATN.repositories.EmployeeRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContractService {
    private final ContractRepository contractRepository;
    private final EmployeeRepository employeeRepository;

    public List<ContractResponseDTO>
            getAllContracts() {

        return contractRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Transactional
    public ContractResponseDTO createContract(
            ContractRequestDTO request) {

        validateContractRequest(request);

        Employee employee =
                employeeRepository.findById(
                        request.getEmployeeId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Không tìm thấy nhân viên"));

        if (contractRepository
                .findByEmployeeIdAndStatus(
                        employee.getId(),
                        "ACTIVE")
                .isPresent()) {

            throw new RuntimeException(
                    "Nhân viên đang có hợp đồng hiện hành");
        }

        Contract contract = new Contract();

        contract.setEmployeeId(
                employee.getId());

        contract.setContractType(
                request.getContractType());

        contract.setSalary(
                request.getSalary());

        contract.setStartDate(
                request.getStartDate());

        contract.setEndDate(
                request.getEndDate());

        contract.setStatus(
                "ACTIVE");

        Contract saved =
                contractRepository.save(
                        contract);

        return toResponseDTO(saved);
    }

    @Transactional
    public ContractResponseDTO extendContract(
            Integer contractId,
            ContractRequestDTO request) {

        Contract oldContract =
                contractRepository.findById(
                        contractId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Không tìm thấy hợp đồng"));

        if (!"ACTIVE".equals(
                oldContract.getStatus())) {

            throw new RuntimeException(
                    "Chỉ được gia hạn hợp đồng ACTIVE");
        }

        validateContractRequest(request);

        oldContract.setStatus(
                "EXPIRED");

        contractRepository.save(
                oldContract);

        Contract newContract =
                new Contract();

        newContract.setEmployeeId(
                oldContract.getEmployeeId());

        newContract.setContractType(
                request.getContractType());

        newContract.setSalary(
                request.getSalary());

        newContract.setStartDate(
                request.getStartDate());

        newContract.setEndDate(
                request.getEndDate());

        newContract.setStatus(
                "ACTIVE");

        Contract saved =
                contractRepository.save(
                        newContract);

        return toResponseDTO(saved);
    }

    @Transactional
    public void cancelContract(
            Integer contractId) {

        Contract contract =
                contractRepository.findById(
                        contractId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Không tìm thấy hợp đồng"));

        if (!"ACTIVE".equals(
                contract.getStatus())) {

            throw new RuntimeException(
                    "Hợp đồng đã bị hủy hoặc hết hạn");
        }

        contract.setStatus(
                "TERMINATED");

        contractRepository.save(
                contract);
    }

    private void validateContractRequest(
            ContractRequestDTO request) {

        if (request.getSalary() == null
                || request.getSalary() <= 0) {

            throw new RuntimeException(
                    "Lương phải lớn hơn 0");
        }

        if (request.getStartDate()
                .isAfter(
                        request.getEndDate())) {

            throw new RuntimeException(
                    "Ngày kết thúc phải lớn hơn ngày bắt đầu");
        }

        List<String> contractTypes =
                List.of(
                        "THU_VIEC",
                        "1_NAM",
                        "3_NAM");

        if (!contractTypes.contains(
                request.getContractType())) {

            throw new RuntimeException(
                    "Loại hợp đồng không hợp lệ");
        }
    }

    private ContractResponseDTO toResponseDTO(
            Contract contract) {

        ContractResponseDTO dto =
                new ContractResponseDTO();

        dto.setId(contract.getId());

        dto.setEmployeeId(
                contract.getEmployeeId());

        Employee employee =
                employeeRepository.findById(
                        contract.getEmployeeId())
                        .orElse(null);

        if (employee != null) {

            dto.setEmployeeName(
                    employee.getFullName());
        }

        dto.setContractType(
                contract.getContractType());

        dto.setSalary(
                contract.getSalary());

        dto.setStartDate(
                contract.getStartDate());

        dto.setEndDate(
                contract.getEndDate());

        dto.setStatus(
                contract.getStatus());

        return dto;
    }
}
