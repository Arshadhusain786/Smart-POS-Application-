package com.arshad.smart_pos_system.service.impl;

import com.arshad.smart_pos_system.config.JwtProvider;
import com.arshad.smart_pos_system.domain.UserRole;
import com.arshad.smart_pos_system.dto.UserDto;
import com.arshad.smart_pos_system.exception.UserException;
import com.arshad.smart_pos_system.mapper.UserMapper;
import com.arshad.smart_pos_system.model.User;
import com.arshad.smart_pos_system.payload.response.AuthResponse;
import com.arshad.smart_pos_system.repository.UserRepository;
import com.arshad.smart_pos_system.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserImplementation customUserImplementation;

    // ============================
    // SIGNUP (Store Owner Only)
    // ============================
    @Override
    public AuthResponse signup(UserDto userDto) {

        // 1️⃣ Check if email already exists
        if (userRepository.findByEmail(userDto.getEmail()) != null) {
            throw new UserException("User already exists with this email");
        }

        // 2️⃣ Validate role (Prevent privilege escalation)
        UserRole requestedRole = userDto.getRole();

        if (requestedRole == UserRole.ROLE_SUPER_ADMIN) {
            throw new UserException("Super Admin role is not allowed for signup");
        }

        // Only ADMIN allowed during public signup
        if (requestedRole != null && requestedRole != UserRole.ROLE_ADMIN) {
            throw new UserException("Invalid role for signup");
        }

        // Default role = ADMIN (Store Owner)
        UserRole finalRole = (requestedRole != null)
                ? requestedRole
                : UserRole.ROLE_ADMIN;

        // 3️⃣ Create new user
        User newUser = new User();
        newUser.setEmail(userDto.getEmail());
        newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        newUser.setRole(finalRole);
        newUser.setFullName(userDto.getFullName());
        newUser.setPhone(userDto.getPhone());
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setUpdatedAt(LocalDateTime.now());
        newUser.setLastLogin(LocalDateTime.now());

        User savedUser = userRepository.save(newUser);

        // 4️⃣ Load UserDetails for JWT
        UserDetails userDetails =
                customUserImplementation.loadUserByUsername(savedUser.getEmail());

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 5️⃣ Generate JWT
        String jwt = jwtProvider.generateToken(authentication);

        // 6️⃣ Prepare Response
        AuthResponse response = new AuthResponse();
        response.setJwt(jwt);
        response.setMessage("User Registered Successfully");
        response.setUser(UserMapper.toDto(savedUser));

        return response;
    }

    // ============================
    // LOGIN
    // ============================
    @Override
    public AuthResponse login(UserDto userDto) {

        String email = userDto.getEmail();
        String password = userDto.getPassword();

        Authentication authentication = authenticate(email, password);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate JWT
        String jwt = jwtProvider.generateToken(authentication);

        // Update last login
        User user = userRepository.findByEmail(email);
        user.setLastLogin(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        // Prepare response
        AuthResponse response = new AuthResponse();
        response.setJwt(jwt);
        response.setMessage("Login successful");
        response.setUser(UserMapper.toDto(user));

        return response;
    }

    // ============================
    // INTERNAL AUTHENTICATION
    // ============================
    private Authentication authenticate(String email, String password) {

        UserDetails userDetails =
                customUserImplementation.loadUserByUsername(email);

        if (userDetails == null) {
            throw new UserException("Email does not exist: " + email);
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new UserException("Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
    }
}
