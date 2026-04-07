package com.cityskate.service;

import com.cityskate.entity.RouteEntity;
import com.cityskate.model.Route;
import com.cityskate.model.RouteRequest;
import com.cityskate.repository.RouteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RouteService {

    private final RouteRepository routeRepository;

    public RouteService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    private Route toDto(RouteEntity r) {
        Route dto = new Route();
        dto.setId(r.getId());
        dto.setName(r.getName());
        dto.setDescription(r.getDescription());
        dto.setDistanceKm(r.getDistanceKm());
        return dto;
    }

    private RouteEntity toEntity(RouteRequest req) {
        RouteEntity r = new RouteEntity();
        r.setName(req.getName());
        r.setDescription(req.getDescription());
        r.setDistanceKm(req.getDistanceKm());
        return r;
    }

    public List<Route> findAll() {
        return routeRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Optional<Route> findById(Long id) {
        return routeRepository.findById(id)
                .map(this::toDto);
    }

    public Route create(RouteRequest request) {
        RouteEntity saved = routeRepository.save(toEntity(request));
        return toDto(saved);
    }
}