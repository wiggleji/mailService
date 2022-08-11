package com.example.mailService.service;

import com.example.mailService.domain.dto.UserSignUpDto;
import com.example.mailService.domain.entity.User;
import com.example.mailService.exception.UserAlreadyExistsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthServiceTest {

    @Autowired
    private static AuthService authService;

    @Test
    public void UserService_registerUser_SUCCESS() throws Exception {
        // given
        UserSignUpDto signUpDto = UserSignUpDto.builder()
                .username("signUpUser")
                .email("signup@test.com")
                .password("testPassword")
                .build();

        // when
        User signUpUser = authService.registerUser(signUpDto);

        // then
        Assertions.assertEquals(signUpUser.getUsername(), signUpDto.getUsername());
        Assertions.assertEquals(signUpUser.getEmail(), signUpDto.getEmail());
        Assertions.assertNotEquals(signUpUser.getPassword(), signUpDto.getPassword());
    }

    @Test
    public void UserService_registerUser_FAIL() throws Exception {
        // given
        UserSignUpDto duplicateSignUpDto = UserSignUpDto.builder()
                .username("signUpUser")
                .email("test@test.com")
                .password("testPassword")
                .build();

        // when

        // then
        Assertions.assertThrows(UserAlreadyExistsException.class, () -> authService.registerUser(duplicateSignUpDto));
    }
}