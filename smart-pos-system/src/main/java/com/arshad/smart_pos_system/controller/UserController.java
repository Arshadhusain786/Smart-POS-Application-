package com.arshad.smart_pos_system.controller;

import com.arshad.smart_pos_system.dto.UserDto;
import com.arshad.smart_pos_system.mapper.UserMapper;
import com.arshad.smart_pos_system.model.User;
import com.arshad.smart_pos_system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // ============================
    // Get Logged-in User Profile
    // ============================
    @GetMapping("/profile")
    public ResponseEntity<UserDto> getUserProfile() {

        User user = userService.getCurrentUser();

        return ResponseEntity.ok(UserMapper.toDto(user));
    }

    // ============================
    // Get User By ID (Admin Only)
    // ============================
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('ADMIN')")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {

        User user = userService.getUserById(id);

        return ResponseEntity.ok(UserMapper.toDto(user));
    }
}
