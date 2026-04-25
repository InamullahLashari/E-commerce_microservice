package com.example.authservice.security;

import com.example.authservice.config.JwtConfig;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtConfig jwtConfig;
    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        byte[] keyBytes = Base64.getDecoder().decode(jwtConfig.getSecret());
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    // ========================
    // CLAIM EXTRACTION
    // ========================

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        return extractAllClaims(token).apply(resolver);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isAccessToken(String token) {
        return "access".equals(extractClaim(token, c -> c.get("type", String.class)));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // ========================
    // TOKEN GENERATION
    // ========================

    public String generateAccessToken(UserDetails userDetails) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "access");

        if (!userDetails.getAuthorities().isEmpty()) {
            String role = userDetails.getAuthorities()
                    .iterator()
                    .next()
                    .getAuthority();
            claims.put("role", role);
        }

        if (userDetails instanceof CustomUserDetails customUser) {
            claims.put("name", customUser.getName());
        }

        return createToken(claims, userDetails.getUsername(), jwtConfig.getAccessTokenExpiration());
    }

    public String generateRefreshToken(UserDetails userDetails) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "refresh");

        return createToken(claims, userDetails.getUsername(), jwtConfig.getRefreshTokenExpiration());
    }

    private String createToken(Map<String, Object> claims, String subject, long expiry) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiry))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    // ========================
    // VALIDATION
    // ========================

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public String getTokenType(String token) {
        return extractClaim(token, c -> c.get("type", String.class));
    }
}