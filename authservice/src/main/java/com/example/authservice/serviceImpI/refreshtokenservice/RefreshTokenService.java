package com.example.authservice.serviceImpI.refreshtokenservice;

import com.example.authservice.entity.RefreshToken;
import com.example.authservice.entity.User;
import com.example.authservice.security.TokenHashUtil;
import com.example.authservice.service.refreshtoken.RefreshTokenRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository repository;

    private final long refreshExpiry = 1000L * 60 * 60 * 24 * 7; // 7 days

    // 🔥 CREATE refresh token
    public String createRefreshToken(User user) {

        String rawToken = UUID.randomUUID().toString();

        RefreshToken token = RefreshToken.builder()
                .tokenHash(TokenHashUtil.hash(rawToken))
                .user(user)
                .expiryDate(Instant.now().plusMillis(refreshExpiry))
                .revoked(false)
                .createdAt(Instant.now())
                .build();

        repository.save(token);

        return rawToken; // return RAW token to client
    }

    // 🔥 VALIDATE refresh token
    public RefreshToken validate(String rawToken) {

        String hash = TokenHashUtil.hash(rawToken);

        RefreshToken token = repository.findByTokenHash(hash)
                .orElseThrow(() -> new EntityNotFoundException("Invalid refresh token"));

        if (token.isRevoked()) {
            throw new RuntimeException("Token revoked");
        }

        if (token.getExpiryDate().isBefore(Instant.now())) {
            throw new RuntimeException("Token expired");
        }

        return token;
    }

    // 🔥 REVOKE token (logout)
    public void revoke(User user) {
        repository.deleteByUserId(user.getId());
    }
}