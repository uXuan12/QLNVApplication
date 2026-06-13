package com.DATN.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DATN.entites.Position;

public interface PositionRepository extends JpaRepository<Position, Integer> {
    boolean existsByDepartmentIdAndTitle(
        Integer departmentId,
        String title);
    Optional<Position>
            findByDepartmentIdAndTitle(
                    Integer departmentId,
                    String title);
    boolean existsByDepartmentId(
            Integer departmentId);
}
