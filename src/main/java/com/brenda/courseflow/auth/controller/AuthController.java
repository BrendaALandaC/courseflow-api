package com.brenda.courseflow.auth.controller;

import com.brenda.courseflow.auth.dto.LoginRequest;
import com.brenda.courseflow.auth.dto.LoginResponse;
import com.brenda.courseflow.auth.dto.RegisterRequest;
import com.brenda.courseflow.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "AuthController", description = "Authentication management for user login and new user registration")
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Register", description = "User registration")
    @PostMapping("/register")
    public void register(
            @RequestBody @Valid RegisterRequest request
    ) {

        authService.register(request);
    }

    @Operation(summary = "Login", description = "User login")
    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody @Valid LoginRequest request
    ) {

        return authService.login(request);
    }
}