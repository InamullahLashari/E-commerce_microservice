package com.example.authservice.auth;


import com.example.authservice.dto.AuthRequestDto;
import com.example.authservice.dto.LoginRequestDto;
import com.example.authservice.dto.RegisterResponse;
import com.example.authservice.mapper.user.UserMapper;
import com.example.authservice.service.auth.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    final private AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDto dto) {
        log.trace("Entering login() with DTO: {}", dto);
        // Delegate to service for business logic
        Map<String, Object> Logresponse = authService.loign(dto);

        log.info("Login successful for email: {}", dto.getEmail());
        log.trace("Exiting login() for email: {}", dto.getEmail());
        return ResponseEntity.ok(Logresponse);
    }


    //=================================User SignUp=============================================//
    @PostMapping("/signup")
    public ResponseEntity<?> signUpUser(@RequestBody @Valid AuthRequestDto ReqDto) {
        log.info("Sign-up attempt with data: {}", ReqDto);
        RegisterResponse response = authService.registere(UserMapper.toEntity(ReqDto));
        log.info("Sign-up successful for email: {}", response);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "status", "success",
                "message", "User added successfully",
                "user", response
        ));

    }
}
