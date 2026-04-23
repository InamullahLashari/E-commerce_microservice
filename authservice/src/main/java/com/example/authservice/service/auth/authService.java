package com.example.authservice.service.auth;

import com.example.authservice.dto.authResponseDto;
import com.example.authservice.dto.loginRequestDto;
import org.apache.catalina.User;

import java.util.Map;

public interface authService {

     authResponseDto registere(User user);

     Map<String ,Object> loign (loginRequestDto req);

     void logout(String email);

     String refreshToken(String refreshToken);




}
