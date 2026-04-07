package com.cityskate.service;

import com.cityskate.entity.RentalPointEntity;
import com.cityskate.model.RentalPoint;
import com.cityskate.repository.RentalPointRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RentalPointService {

    private final RentalPointRepository repository;

    public RentalPointService(RentalPointRepository repository) {
        this.repository = repository;
    }

    private RentalPoint toDto(RentalPointEntity e) {
        RentalPoint dto = new RentalPoint();
        dto.setId(e.getId());
        dto.setName(e.getName());
        dto.setLat(e.getLat());
        dto.setLng(e.getLng());
        dto.setAddress(e.getAddress());
        return dto;
    }

    private RentalPointEntity toEntity(RentalPoint dto) {
        RentalPointEntity e = new RentalPointEntity();
        e.setName(dto.getName());
        e.setLat(dto.getLat());
        e.setLng(dto.getLng());
        e.setAddress(dto.getAddress());
        return e;
    }

    public List<RentalPoint> findAll() {
        return repository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Optional<RentalPoint> findById(Long id) {
        return repository.findById(id).map(this::toDto);
    }

    public RentalPoint create(RentalPoint dto) {
        return toDto(repository.save(toEntity(dto)));
    }
}