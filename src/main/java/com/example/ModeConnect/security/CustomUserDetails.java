package com.example.ModeConnect.security;

import com.example.ModeConnect.model.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
@RequiredArgsConstructor
@Getter
public class CustomUserDetails implements UserDetails {
    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(user.getRole()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail(); // ou username si tu veux
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // adapter si besoin
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // adapter si besoin
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // adapter si besoin
    }

    @Override
    public boolean isEnabled() {
        return true; // adapter si besoin
    }

    public Long getId() {
       return user.getId();
    }
}
