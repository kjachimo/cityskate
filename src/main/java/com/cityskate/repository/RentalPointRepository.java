package com.cityskate.repository;

import com.cityskate.entity.RentalPointEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalPointRepository extends JpaRepository<RentalPointEntity, Long> {
}