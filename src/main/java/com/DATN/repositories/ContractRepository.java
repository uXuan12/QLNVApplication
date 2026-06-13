package com.DATN.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DATN.entites.Contract;

public interface ContractRepository  extends JpaRepository<Contract,Integer>{
    Optional<Contract>
            findByEmployeeIdAndStatus(
                    Integer employeeId,
                    String status);
}
