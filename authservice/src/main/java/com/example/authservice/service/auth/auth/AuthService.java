package com.example.authservice.service.auth.auth;

import com.example.authservice.dto.AuthResponseDto;
import com.example.authservice.dto.LoginRequestDto;
import org.apache.catalina.User;

import java.util.Map;

public interface AuthService {

     AuthResponseDto registere(User user);

     Map<String ,Object> loign (LoginRequestDto req);

     void logout(String email);

     String refreshToken(String refreshToken);




}
