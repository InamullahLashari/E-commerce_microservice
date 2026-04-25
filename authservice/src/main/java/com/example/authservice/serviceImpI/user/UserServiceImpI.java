package com.example.authservice.serviceImpI.user;

import com.example.authservice.dto.RegisterResponse;
import com.example.authservice.dto.LoginRequestDto;
import com.example.authservice.entity.User;
import com.example.authservice.exception.InvalidActionException;
import com.example.authservice.mapper.user.UserMapper;
import com.example.authservice.model.Role;
import com.example.authservice.repository.UserRepository;
import com.example.authservice.security.JwtTokenProvider;
import com.example.authservice.service.auth.AuthService;

import com.example.authservice.serviceImpI.customImp.CustomUserDetailServiceImp;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class UserServiceImpI implements AuthService {

    private final  UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
private final CustomUserDetailServiceImp customsservice;
private final JwtTokenProvider jwtTokenProvider;

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

        User user = userRepo.findByEmailIgnoreCase(req.getEmail())
                .orElseThrow(()->new EntityNotFoundException("user not found"));

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new InvalidActionException("Invalid password");
        }

        UserDetails userDetails = customsservice.loadUserByUsername(req.getEmail());
        String token =  jwtTokenProvider.generateRefreshToken(userDetails);
        String refreshToken = jwtTokenProvider.generateRefreshToken(userDetails);






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
