package com.cityskate.controller;

import com.cityskate.model.UpdateProfileRequest;
import com.cityskate.model.UserProfile;
import com.cityskate.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public UserProfile getCurrentUser() {
        return userService.getCurrentUser();
    }

    @PutMapping("/me")
    public UserProfile updateCurrentUser(@RequestBody UpdateProfileRequest request) {
        return userService.updateCurrentUser(request);
    }
}