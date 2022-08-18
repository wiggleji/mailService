package com.example.mailService.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserSignUpDto {

    private String username;

    private String email;

    private String password;
}
