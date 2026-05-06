package com.bitsandbytes.apigateway.filter;

import com.bitsandbytes.apigateway.security.JwtTokenProvider;
import com.bitsandbytes.apigateway.service.RouteValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@Order(-100)
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements WebFilter {

    private final JwtTokenProvider tokenProvider;
    private final RouteValidator routeValidator;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        // 1. Public route → skip authentication

        System.out.println("This is chcek:" + routeValidator.isPublicRoute(path));

        if (routeValidator.isPublicRoute(path)) {
            log.debug("Public route accessed: {}", path);
            return chain.filter(exchange);
        }

        log.info("Protected route accessed: {}", path);

        // 2. Extract token
        String token = extractToken(request);

        if (token == null) {
            return unauthorized(exchange, "Missing token");
        }

        // 3. Validate token
        if (!tokenProvider.validateToken(token)) {
            return unauthorized(exchange, "Invalid token");
        }

        // 4. Extract user info (optional but recommended)
        String email;
        try {
            email = tokenProvider.getEmailFromToken(token);
        } catch (Exception e) {
            return unauthorized(exchange, "Invalid token payload");
        }

        // 5. Add user info to request header
        ServerHttpRequest mutatedRequest = request.mutate()
                .header("X-User-Email", email)
                .build();

        log.info("Authenticated user: {} → {}", email, path);

        // 6. Continue filter chain
        return chain.filter(exchange.mutate().request(mutatedRequest).build());
    }

    // ================= UNAUTHORIZED RESPONSE =================
    private Mono<Void> unauthorized(ServerWebExchange exchange, String message) {
        log.error("Unauthorized: {}", message);
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    // ================= TOKEN EXTRACTION =================
    private String extractToken(ServerHttpRequest request) {
        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }

        return null;
    }
}