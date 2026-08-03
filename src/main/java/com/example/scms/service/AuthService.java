package com.example.scms.service;

import com.example.scms.dto.LoginRequest;
import com.example.scms.dto.LoginResponse;
import com.example.scms.dto.RegisterRequest;

public interface AuthService {

    String register(RegisterRequest registerRequest);
    LoginResponse login(LoginRequest loginRequest);
}
