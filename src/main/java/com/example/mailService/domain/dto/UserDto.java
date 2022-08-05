package com.example.mailService.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class UserDto {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class UserInfoDto {
        private String username;
        private String email;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class UserLoginDto {
        private String email;
        private String password;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class UserSignUpDto {
        private String username;
        private String email;
        private String password;
    }
}
