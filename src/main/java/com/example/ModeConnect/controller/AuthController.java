package com.example.ModeConnect.controller;

import com.example.ModeConnect.DTO.request.LoginRequest;
import com.example.ModeConnect.DTO.request.RegisterRequest;
import com.example.ModeConnect.DTO.response.LoginResponse;
import com.example.ModeConnect.service.implementation.AuthentificationImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthentificationImpl authService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(
            @RequestBody RegisterRequest request
    ) {
        authService.register(request);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Registration successful");
        // remplace par l'id réel
        return ResponseEntity.ok(response);

    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request
    ) {
        return ResponseEntity.ok(authService.login(request));
    }
}
