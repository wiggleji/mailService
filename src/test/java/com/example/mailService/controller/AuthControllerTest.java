package com.example.mailService.controller;

import com.example.mailService.base.BaseTestSetup;
import com.example.mailService.user.controller.AuthController;
import com.example.mailService.user.dto.UserDto;
import com.example.mailService.user.dto.UserLoginDto;
import com.example.mailService.user.dto.UserSignUpDto;
import com.example.mailService.exception.UserAlreadyExistsException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class AuthControllerTest extends BaseTestSetup {

    @Autowired
    private AuthController authController;

    private UserLoginDto testUserLoginDto() {
        return UserLoginDto.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .build();
    }

    private UserLoginDto testUserLoginDto(String username) {
        return UserLoginDto.builder()
                .username(username)
                .password(PASSWORD)
                .build();
    }

    private UserSignUpDto testUserSignUpDto() {
        return UserSignUpDto.builder()
                .username(USERNAME)
                .email(EMAIL)
                .password(PASSWORD)
                .build();
    }

    private UserSignUpDto testUserSignUpDto(String username, String email) {
        return UserSignUpDto.builder()
                .username(username)
                .email(email)
                .password(PASSWORD)
                .build();
    }

    @Test
    public void testAuthControllerUserLogin_SUCCESS() {
        // given
        UserLoginDto loginDto = testUserLoginDto();

        // when
        ResponseEntity<?> response = authController.userLogin(loginDto);

        // then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getHeaders().getFirst("Authorization")).startsWith("Bearer ");
    }

    @Test
    public void testAuthControllerUserLogin_FAIL() throws Exception {
        // given
        UserLoginDto loginDto = testUserLoginDto("WRONG_USER");

        // when
        ResponseEntity<?> response = authController.userLogin(loginDto);

        // then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void testAuthControllerUserSignUp_SUCCESS() {
        // given
        UserSignUpDto signUpDto = testUserSignUpDto("NEW_USER","new@test.com");

        // when
        UserDto userDto = authController.userSignUp(signUpDto);

        // then
        Assertions.assertThat(userDto.getUsername()).isEqualTo(signUpDto.getUsername());
        Assertions.assertThat(userDto.getEmail()).isEqualTo(signUpDto.getEmail());
    }

    @Test
    public void testAuthControllerUserSignUp_FAIL() throws Exception {
        // given
        UserSignUpDto signUpDto = testUserSignUpDto();

        // when

        // then
        org.junit.jupiter.api.Assertions.assertThrows(UserAlreadyExistsException.class, () -> authController.userSignUp(signUpDto));
    }
}
