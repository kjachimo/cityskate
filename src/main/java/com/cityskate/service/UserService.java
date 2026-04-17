package com.cityskate.service;

import com.cityskate.entity.UserEntity;
import com.cityskate.model.UserProfile;
import com.cityskate.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ===== ENTITY → DTO =====
    private UserProfile toDto(UserEntity u) {
        UserProfile dto = new UserProfile();
        dto.setId(u.getId());
        dto.setUsername(u.getUsername());
        dto.setEmail(u.getEmail());

        if (u.getCreatedAt() != null) {
            dto.setCreatedAt(OffsetDateTime.parse(u.getCreatedAt()));
        }

        return dto;
    }

    // ===== DTO → ENTITY =====
    private UserEntity toEntity(UserProfile dto) {
        UserEntity u = new UserEntity();
        u.setUsername(dto.getUsername());
        u.setEmail(dto.getEmail());

        if (dto.getCreatedAt() != null) {
            u.setCreatedAt(dto.getCreatedAt().toString());
        } else {
            u.setCreatedAt(OffsetDateTime.now().toString());
        }

        return u;
    }

    // ===== GET ALL =====
    public List<UserProfile> findAll() {
        return userRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // ===== GET BY ID =====
    public Optional<UserProfile> findById(Long id) {
        return userRepository.findById(id)
                .map(this::toDto);
    }

    // ===== CREATE =====
    public UserProfile create(UserProfile request) {
        UserEntity saved = userRepository.save(toEntity(request));
        return toDto(saved);
    }

    // ===== UPDATE =====
    public Optional<UserProfile> update(Long id, UserProfile request) {
        return userRepository.findById(id).map(existing -> {

            existing.setUsername(request.getUsername());
            existing.setEmail(request.getEmail());

            if (request.getCreatedAt() != null) {
                existing.setCreatedAt(request.getCreatedAt().toString());
            }

            return toDto(userRepository.save(existing));
        });
    }

    // ===== DELETE =====
    public boolean delete(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}