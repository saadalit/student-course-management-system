package com.example.scms.service.impl;

import com.example.scms.dto.LoginRequest;
import com.example.scms.dto.LoginResponse;
import com.example.scms.dto.RegisterRequest;
import com.example.scms.entity.User;
import com.example.scms.exception.DuplicateRecordException;
import com.example.scms.exception.UnauthorizedException;
import com.example.scms.repository.UserRepository;
import com.example.scms.security.JwtUtils;
import com.example.scms.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Override
    public String register(RegisterRequest request) {
        log.info("Attempting registration for username: {}", request.getUsername());

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateRecordException("Username already exists: " + request.getUsername());
        }

        User user = new User();
        user.setUsername(request.getUsername());
        // BCrypt Hash Password
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        userRepository.save(user);
        log.info("AUDIT: User successfully registered with BCrypt hashing - Username: {}, Role: {}", user.getUsername(), user.getRole());

        return "User registered successfully";
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        log.info("AUDIT: Login attempt for username: {}", request.getUsername());

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UnauthorizedException("Invalid username or password"));

        // Verify BCrypt Password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            log.warn("AUDIT: Login failed for username: {} - Incorrect password", request.getUsername());
            throw new UnauthorizedException("Invalid username or password");
        }

        // Generate JWT Token
        String token = jwtUtils.generateToken(user.getUsername(), user.getRole().name());

        log.info("AUDIT: Login successful for username: {} - Token Issued", user.getUsername());

        return new LoginResponse("Bearer", token, user.getUsername(), user.getRole());
    }
}