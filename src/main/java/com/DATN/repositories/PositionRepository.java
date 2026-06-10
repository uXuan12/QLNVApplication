package com.DATN.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DATN.entites.Position;

public interface PositionRepository extends JpaRepository<Position, Integer> {

}
