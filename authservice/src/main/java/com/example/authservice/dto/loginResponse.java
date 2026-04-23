package com.example.authservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class loginResponse {
    private String accessToken;
    private String refreshToken;
    private String tokenType;        // always "Bearer"
    private long   expiresIn;        // seconds
    private Long   userId;
    private String email;
}
