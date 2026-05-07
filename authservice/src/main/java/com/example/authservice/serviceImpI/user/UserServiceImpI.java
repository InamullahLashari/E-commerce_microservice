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
import com.example.authservice.serviceImpI.refreshtokenservice.RefreshTokenService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class UserServiceImpI implements AuthService {

    private final  UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailServiceImp customsservice;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;

    //===================================registered=========================



    @Override
    public RegisterResponse registere(User user) {


        userRepo.findByEmailIgnoreCase(user.getEmail())
                .ifPresent(u -> {
                    throw new InvalidActionException("User already exists with this email");
                });


        User saved = User.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .roles(Set.of(Role.ROLE_USER)) // default role
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
        String accessToken = jwtTokenProvider.generateAccessToken(userDetails);
        String refreshToken = refreshTokenService.createRefreshToken(user);

        List<String> roleType = user.getRoles()
                .stream()
                .map(Enum::name)
                .toList();


        Map<String, String> tokens = new LinkedHashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "success");
        response.put("message", roleType + " login successful");
        response.put("role", roleType);
        response.put("tokens", tokens);

        return response;
    }

    @Override
    public void logout(String email) {

    }

    @Override
    public String refreshToken(String refreshToken) {
        return "";
    }





}
