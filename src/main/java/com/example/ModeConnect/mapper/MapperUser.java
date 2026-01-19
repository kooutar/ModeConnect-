package com.example.ModeConnect.mapper;

import com.example.ModeConnect.DTO.response.LoginResponse;
import com.example.ModeConnect.model.User;
import org.mapstruct.*;


@Mapper(componentModel = "spring")
public interface MapperUser {
    // Conversion User -> LoginResponse (sans les tokens)
    @Mapping(target = "token", ignore = true)
    @Mapping(target = "refreshToken", ignore = true)
    @Mapping(source = "username", target = "username")
    @Mapping(expression = "java(user.getRole())", target = "role")
    LoginResponse toLoginResponse(User user);

    // Méthode helper pour créer un LoginResponse complet
    default LoginResponse toLoginResponse(User user, String token, String refreshToken) {
        LoginResponse response = toLoginResponse(user);
        response.setToken(token);
        response.setRefreshToken(refreshToken);
        return response;
    }
}
