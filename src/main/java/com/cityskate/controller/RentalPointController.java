package com.cityskate.controller;

import com.cityskate.model.RentalPoint;
import com.cityskate.service.RentalPointService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rental-points")
public class RentalPointController {

    private final RentalPointService service;

    public RentalPointController(RentalPointService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<RentalPoint>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    public ResponseEntity<RentalPoint> create(@RequestBody RentalPoint dto) {
        return ResponseEntity.status(201).body(service.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalPoint> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}