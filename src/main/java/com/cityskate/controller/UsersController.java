package com.cityskate.controller;

import com.cityskate.api.UsersApi;
import com.cityskate.model.UserProfile;
import com.cityskate.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UsersController implements UsersApi {

    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }


    public ResponseEntity<List<UserProfile>> getUsers() {
        return ResponseEntity.ok(userService.findAll());
    }


    public ResponseEntity<UserProfile> getUserById(Long userId) {
        return userService.findById(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    public ResponseEntity<UserProfile> createUser(UserProfile userProfile) {
        return ResponseEntity.status(201).body(userService.create(userProfile));
    }
}