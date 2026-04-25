package com.example.authservice.service.auth;

import com.example.authservice.dto.RegisterResponse;
import com.example.authservice.dto.loginResponse;
import com.example.authservice.dto.LoginRequestDto;
import com.example.authservice.entity.User;


import java.util.Map;

public interface AuthService {

     RegisterResponse registere(User user );

     Map<String ,Object> loign (LoginRequestDto req);

     void logout(String email);

     String refreshToken(String refreshToken);




}
