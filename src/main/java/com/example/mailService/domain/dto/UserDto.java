package com.example.mailService.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;

public class UserDto {

    @Builder
    @AllArgsConstructor
    public static class UserInfo {
        private String username;
        private String email;
    }

    @Builder
    @AllArgsConstructor
    public static class UserLogin {
        private String email;
        private String password;
    }

    @Builder
    @AllArgsConstructor
    public static class UserSignUp {
        private String username;
        private String email;
        private String password;
    }
}
