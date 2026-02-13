package com.example.ModeConnect.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {
    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.expiration}")
    private long expirationTime;
    @Value("${jwt.refresh-expiration}")
    private long refreshExpirationTime;

  private SecretKey getSecretKey(){
      return Keys.hmacShaKeyFor(secretKey.getBytes());
  }

  public String extractUsername(String token){

      return extractClaim(token, Claims::getSubject);
  }

    private <T> T extractClaim(java.lang.String token, Function<Claims,T> claimsResolver) {
      final  Claims claims=extractAllClaims(token);
      return  claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
      return Jwts.parser()
              .verifyWith(getSecretKey())
              .build()
              .parseSignedClaims(token)
              .getPayload();
    }

    private Boolean isTokenExpired(String token){
      return  extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String generateToken(UserDetails userDetails, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role); // ajoute le rôle
        return createToken(claims, userDetails.getUsername(), expirationTime);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername(), refreshExpirationTime);
    }

    private String createToken(Map<String, Object> claims, String subject, Long expiration) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSecretKey())
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }

}
