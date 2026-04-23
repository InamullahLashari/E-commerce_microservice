package com.example.authservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class loginRequestDto {


    @NotNull
    private String email;
    @NotNull
    private String password;
}
