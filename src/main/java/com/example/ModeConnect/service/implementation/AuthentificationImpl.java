package com.example.ModeConnect.service.implementation;

import com.example.ModeConnect.DTO.request.LoginRequest;
import com.example.ModeConnect.DTO.request.RegisterRequest;
import com.example.ModeConnect.DTO.response.LoginResponse;
import com.example.ModeConnect.Repository.UserRepository;
import com.example.ModeConnect.mapper.MapperUser;
import com.example.ModeConnect.model.Client;
import com.example.ModeConnect.model.Creator;
import com.example.ModeConnect.model.User;
import com.example.ModeConnect.security.CustomUserDetails;
import com.example.ModeConnect.security.JwtService;
import com.example.ModeConnect.service.interfaces.AuthentificationInterface;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthentificationImpl  implements AuthentificationInterface {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MapperUser registerMapper;
    private final JwtService jwtService;
    private final MapperUser mapperUser;

    public String register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user;

        if ("ROLE_CLIENT".equals(request.getRole())) {
            user = new Client();
        } else if ("ROLE_CREATOR".equals(request.getRole())) {
            user = new Creator();
        } else {
            throw new IllegalArgumentException("Invalid role");
        }

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User savedUser = userRepository.save(user);

        return "registration evec succes for user id: " + savedUser.getId();
    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Email not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
      CustomUserDetails userDetails= new CustomUserDetails(user);
        // Génération des tokens JWT
        String token = jwtService.generateToken(userDetails,user.getRole());
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        // Mapper User -> LoginResponse et ajouter tokens
        return mapperUser.toLoginResponse(user, token, refreshToken);
    }
}
