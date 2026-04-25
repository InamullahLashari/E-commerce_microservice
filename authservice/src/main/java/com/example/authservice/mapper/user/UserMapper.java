package com.example.authservice.mapper.user;
import com.example.authservice.dto.AuthRequestDto;
import com.example.authservice.dto.RegisterResponse;
import com.example.authservice.entity.User;

public class UserMapper {


    public static RegisterResponse toDto(User user) {

        RegisterResponse resp = RegisterResponse.builder()
                .email(user.getEmail())
                .userId(user.getId())

                .build();
        return resp;

    }
public static User toEntity(AuthRequestDto auth){

        User user = User.builder()
                .email(auth.getEmail())
                .firstName(auth.getFirstName())
                .password(auth.getPassword())
                .lastName(auth.getLastName())
                .build();
        return user;


        }




}
