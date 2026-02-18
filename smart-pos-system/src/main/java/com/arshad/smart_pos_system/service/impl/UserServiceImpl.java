package com.arshad.smart_pos_system.service.impl;

import com.arshad.smart_pos_system.exception.UserException;
import com.arshad.smart_pos_system.model.User;
import com.arshad.smart_pos_system.repository.UserRepository;
import com.arshad.smart_pos_system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getCurrentUser() {

        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || auth.getName() == null) {
            throw new UserException("User not authenticated");
        }

        String email = auth.getName();

        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UserException("User not found");
        }

        return user;
    }

    @Override
    public User getUserByEmail(String email) {

        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UserException("User not found");
        }

        return user;
    }

    @Override
    public User getUserById(Long id) {

        return userRepository.findById(id)
                .orElseThrow(() ->
                        new UserException("User not found"));
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }
}
