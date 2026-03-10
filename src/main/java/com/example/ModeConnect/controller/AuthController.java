package com.example.ModeConnect.controller;

import com.example.ModeConnect.DTO.request.LoginRequest;
import com.example.ModeConnect.DTO.request.RegisterRequest;
import com.example.ModeConnect.DTO.response.LoginResponse;
import com.example.ModeConnect.security.CustomUserDetails;
import com.example.ModeConnect.security.JwtService;
import com.example.ModeConnect.security.jwtFilter;
import com.example.ModeConnect.service.implementation.AuthentificationImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthentificationImpl authService;
    private final AuthenticationManager authenticationManager;
    private final jwtFilter jwtFilter;
    private final JwtService jwtService;

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
            @RequestBody LoginRequest request,
            HttpServletResponse response
    ) {

            // 1. Authentification via AuthService (qui fait tout)
            LoginResponse loginResponse = authService.login(request);

            return ResponseEntity.ok(loginResponse);


    }
}
