package com.cityskate.service;

import com.cityskate.entity.UserEntity;
import com.cityskate.model.UpdateProfileRequest;
import com.cityskate.model.UserProfile;
import com.cityskate.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    private static final Long CURRENT_USER_ID = 1L;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // DTO mapping
    private UserProfile toDto(UserEntity u) {
        UserProfile dto = new UserProfile();
        dto.setId(u.getId());
        dto.setUsername(u.getUsername());
        dto.setEmail(u.getEmail());
        dto.setRole(UserProfile.RoleEnum.valueOf(u.getRole().name()));
        if (u.getCreatedAt() != null)
            dto.setCreatedAt(u.getCreatedAt());
        return dto;
    }

    public UserProfile getCurrentUser() {
        UserEntity user = userRepository.findById(CURRENT_USER_ID)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return toDto(user);
    }

    public UserProfile updateCurrentUser(UpdateProfileRequest request) {
        UserEntity user = userRepository.findById(CURRENT_USER_ID)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (request.getUsername() != null) {
            user.setUsername(request.getUsername());
        }

        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }

        return toDto(userRepository.save(user));
    }

    // pomocnicze – do testów / register
    public UserProfile createTestUser(String username, String email) {
        UserEntity u = new UserEntity();
        u.setUsername(username);
        u.setEmail(email);
        return toDto(userRepository.save(u));
    }
}