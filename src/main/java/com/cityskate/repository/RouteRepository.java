package com.cityskate.repository;

import com.cityskate.entity.RouteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteRepository extends JpaRepository<RouteEntity, Long> {
}