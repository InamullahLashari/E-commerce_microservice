package com.example.authservice.serviceImpI;

import com.example.authservice.dto.RegisterResponse;
import com.example.authservice.dto.loginResponse;
import com.example.authservice.dto.LoginRequestDto;
import com.example.authservice.entity.User;
import com.example.authservice.mapper.user.UserMapper;
import com.example.authservice.model.Role;
import com.example.authservice.repository.UserRepository;
import com.example.authservice.service.auth.auth.AuthService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class UserServiceImpI implements AuthService {

    private final  UserRepository userRepo;
    private final PasswordEncoder        passwordEncoder;

    //===================================registered=========================



    @Override
    public RegisterResponse registere(User user) {
        User Saveduser = userRepo.findByEmailIgnoreCase(user.getEmail()).
      orElseThrow(()-> new EntityNotFoundException("User allready avalable"));


        User saved = User.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .roles(Set.of(Role.ROLE_USER))
                .build();

        return UserMapper.toDto(userRepo.save(saved));
    }

    @Override
    public Map<String, Object> loign(LoginRequestDto req) {

        User usersaved = userRepo.findByEmailIgnoreCase(req.getEmail())
                .orElseThrow(()->new EntityNotFoundException("user not found"));


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
