package com.example.ModeConnect.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginResponse {
     private  String token;
     private  String refreshToken;
     private  String username;
     private  String role;
}
