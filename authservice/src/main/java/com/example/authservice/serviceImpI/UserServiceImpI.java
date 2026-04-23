package com.example.authservice.serviceImpI;

import com.example.authservice.dto.AuthResponseDto;
import com.example.authservice.dto.LoginRequestDto;
import com.example.authservice.repository.UserRepository;
import com.example.authservice.service.auth.auth.AuthService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserServiceImpI implements AuthService {

    @Autowired
    private UserRepository userRepo;



    @Override
    public AuthResponseDto registere(User user) {





        return null;
    }

    @Override
    public Map<String, Object> loign(LoginRequestDto req) {
        return Map.of();
    }

    @Override
    public void logout(String email) {

    }

    @Override
    public String refreshToken(String refreshToken) {
        return "";
    }
}
