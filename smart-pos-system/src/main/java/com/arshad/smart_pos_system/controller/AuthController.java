package com.arshad.smart_pos_system.controller;

import com.arshad.smart_pos_system.dto.UserDto;
import com.arshad.smart_pos_system.exception.UserException;
import com.arshad.smart_pos_system.payload.response.AuthResponse;
import com.arshad.smart_pos_system.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController
{
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signupHandler(@RequestBody UserDto userDto) throws Exception
    {
        return ResponseEntity.ok(authService.signup(userDto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginHandler(@RequestBody UserDto userDto) throws Exception
    {
        return ResponseEntity.ok(authService.login(userDto));
    }
}
