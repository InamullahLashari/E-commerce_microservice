package com.bitsandbytes.apigateway.service; ////package com.example.gateway.service;
////
////import lombok.Data;
////import lombok.extern.slf4j.Slf4j;
////import org.springframework.boot.context.properties.ConfigurationProperties;
////import org.springframework.cache.annotation.Cacheable;
////import org.springframework.stereotype.Component;
////import org.springframework.util.AntPathMatcher;
////
////import java.util.ArrayList;
////import java.util.List;
////import java.util.concurrent.ConcurrentHashMap;
////
////@Slf4j
////@Data
////@Component
////@ConfigurationProperties(prefix = "gateway")
////public class RouteValidator {
////
////    private List<String> publicRoutes = new ArrayList<>();
////    private final AntPathMatcher pathMatcher = new AntPathMatcher();
////
////    // Cache for route matching decisions
////    private final ConcurrentHashMap<String, Boolean> routeCache = new ConcurrentHashMap<>();
////
////    @Cacheable(value = "routeCache", key = "#path")
////    public boolean isPublicRoute(String path) {
////        // Check cache first
////        Boolean cached = routeCache.get(path);
////        if (cached != null) {
////            return cached;
////        }
////
////        boolean isPublic = publicRoutes.stream()
////                .anyMatch(route -> pathMatcher.match(route, path));
////
////        // Cache the result
////        routeCache.put(path, isPublic);
////
////        if (isPublic) {
////            log.debug("Public route: {}", path);
////        }
////
////        return isPublic;
////    }
////
////    public boolean isSecuredRoute(String path) {
////        return !isPublicRoute(path);
////    }
////
////    // Clear cache if routes change (for dynamic config)
////    public void clearCache() {
////        routeCache.clear();
////        log.info("Route cache cleared");
////    }
////}
//
//
//package com.example.gateway.service;
//
//import jakarta.annotation.PostConstruct;
//import lombok.Data;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.stereotype.Component;
//import org.springframework.util.AntPathMatcher;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.ConcurrentHashMap;
//
//@Slf4j
//@Data
//@Component
//@ConfigurationProperties(prefix = "gateway")
//public class RouteValidator {
//
//    private List<String> publicRoutes = new ArrayList<>();
//    private final AntPathMatcher pathMatcher = new AntPathMatcher();
//    private final ConcurrentHashMap<String, Boolean> routeCache = new ConcurrentHashMap<>();
//
//    @PostConstruct
//    public void init() {
//        log.info("========================================");
//        log.info("✅ RouteValidator Initialized");
//        log.info("📋 Public Routes Count: {}", publicRoutes.size());
//        log.info("📋 Public Routes Configured:");
//        publicRoutes.forEach(route -> log.info("   - {}", route));
//        log.info("========================================");
//    }
//
//    public boolean isPublicRoute(String path) {
//        // Check cache first
//        Boolean cached = routeCache.get(path);
//        if (cached != null) {
//            return cached;
//        }
//
//        boolean isPublic = publicRoutes.stream()
//                .anyMatch(route -> pathMatcher.match(route, path));
//
//        // Cache the result
//        routeCache.put(path, isPublic);
//
//        log.debug("Path: {} -> isPublic: {}", path, isPublic);
//
//        return isPublic;
//    }
//
//    public boolean isSecuredRoute(String path) {
//        return !isPublicRoute(path);
//    }
//
//    public void clearCache() {
//        routeCache.clear();
//        log.info("Route cache cleared");
//

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Data
@Component
@ConfigurationProperties(prefix = "gateway")
public class RouteValidator {

    private List<String> publicRoutes = new ArrayList<>();
    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    private final ConcurrentHashMap<String, Boolean> routeCache = new ConcurrentHashMap<>();

    // Hardcoded fallback public routes
    private static final List<String> HARDCODED_PUBLIC_ROUTES = Arrays.asList(
            "/quick-debug",
            "/redis-keys",
            "/redis-info",
            "/actuator/**",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/webjars/**",
            "/api/auth/**",
            "/api/users/**",
            "/fallback/**"
    );

    @PostConstruct
    public void init() {
        // Merge hardcoded routes with configured routes
        HARDCODED_PUBLIC_ROUTES.forEach(route -> {
            if (!publicRoutes.contains(route)) {
                publicRoutes.add(route);
            }
        });

        log.info("========================================");
        log.info("✅ RouteValidator Initialized");
        log.info("📋 Public Routes Count: {}", publicRoutes.size());
        log.info("📋 Public Routes Configured:");
        publicRoutes.forEach(route -> log.info("   - {}", route));
        log.info("========================================");
    }

    public boolean isPublicRoute(String path) {
        // Quick debug endpoints check
        if (path.startsWith("/quick-debug") ||
                path.startsWith("/redis-keys") ||
                path.startsWith("/redis-info")) {
            return true;
        }

        // Check cache first
        Boolean cached = routeCache.get(path);
        if (cached != null) {
            return cached;
        }

        boolean isPublic = publicRoutes.stream()
                .anyMatch(route -> pathMatcher.match(route, path));

        // Cache the result
        routeCache.put(path, isPublic);

        if (isPublic) {
            log.debug("✅ Public route matched: {}", path);
        }

        return isPublic;
    }

    public boolean isSecuredRoute(String path) {
        return !isPublicRoute(path);
    }

    public void clearCache() {
        routeCache.clear();
        log.info("Route cache cleared");
    }
}

