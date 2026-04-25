package com.example.authservice.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Objects;

public class CustomUserDetails implements UserDetails {

    private final Long userId;
    private final String name;
    private final String email;
    private final String password;

    private final boolean enabled;
    private final boolean accountNonLocked;

    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(
            Long userId,
            String name,
            String email,
            String password,
            boolean enabled,
            boolean accountNonLocked,
            Collection<? extends GrantedAuthority> authorities
    ) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.accountNonLocked = accountNonLocked;
        this.authorities = authorities;
    }

    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // optional future use
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // optional
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    // 🔥 Important for security comparisons
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomUserDetails that)) return false;
        return Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}