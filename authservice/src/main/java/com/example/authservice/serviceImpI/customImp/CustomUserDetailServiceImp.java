package com.example.authservice.serviceImpI.customImp;

import com.example.authservice.entity.User;
import com.example.authservice.repository.UserRepository;
import com.example.authservice.security.CustomUserDetails;
import com.example.authservice.service.customuserDetailService.CustomUserDetailService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomUserDetailServiceImp implements CustomUserDetailService {


    private final UserRepository userRepo;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepo.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found with this email"+ email));
        List<SimpleGrantedAuthority> authorities =
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                        .toList();
        return new CustomUserDetails(
                user.getId(),
                user.getFirstName(),
                user.getEmail(),
                user.getPassword(),
                user.isEnabled(),
                user.isAccountNonLocked(),
                authorities
        );
    }
}
