package com.example.gateway.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.constraints.NotBlank;

@Data
@Configuration
@Validated
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {

    @NotBlank(message = "JWT secret cannot be empty")
    private String secret;

    private Long accessTokenExpiration = 900000L;
    private String header = "Authorization";
    private String prefix = "Bearer ";

    public String getPrefix() {
        return prefix;
    }

    public String getFullPrefix() {
        return prefix + " ";
    }
}