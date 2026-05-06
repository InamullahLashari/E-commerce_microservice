package com.bitsandbytes.apigateway.service;

import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import java.util.List;

@Service
public class RouteValidator {

    private static final AntPathMatcher pathMatcher = new AntPathMatcher();

    private static final List<String> PUBLIC_ENDPOINTS = List.of(
            "/api/auth/login",
            "/api/auth/sigup",
            "/api/auth/refresh",
            "/api/auth/logout",
            "/api/auth/verify-email",
            "/api/auth/forgot-password",
            "/api/auth/reset-password",
            "/actuator/health/**",
            "/actuator/info",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/fallback/**"
    );

    public boolean isPublicRoute(String path) {
        boolean isPublic = PUBLIC_ENDPOINTS.stream().anyMatch(route -> pathMatcher.match(route, path));
        if (isPublic) {
            System.out.println("✅ Public route detected: " + path);
        } else {
            System.out.println("🔒 Protected route detected: " + path);
        }
        return isPublic;
    }

    public boolean isSecuredRoute(String path) {
        return !isPublicRoute(path);
    }
}