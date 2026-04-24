package com.example.authservice.mapper.user;
import com.example.authservice.dto.RegisterResponse;
import com.example.authservice.entity.User;

public class UserMapper {


    public static RegisterResponse toDto(User user){

        RegisterResponse resp = RegisterResponse.builder()
                .email(user.getEmail())
                .userId(user.getId())
                .build();
        return resp;




    }


}
