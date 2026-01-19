package com.example.ModeConnect.controller;

import com.example.ModeConnect.DTO.request.LoginRequest;
import com.example.ModeConnect.DTO.request.RegisterRequest;
import com.example.ModeConnect.DTO.response.LoginResponse;
import com.example.ModeConnect.service.implementation.AuthentificationImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthentificationImpl authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request
    ) {
        return ResponseEntity.ok(authService.login(request));
    }
}
