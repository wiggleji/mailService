package com.example.mailService.controller;

import com.example.mailService.domain.dto.UserDto;
import com.example.mailService.domain.dto.UserLoginDto;
import com.example.mailService.domain.dto.UserSignUpDto;
import com.example.mailService.domain.entity.User;
import com.example.mailService.domain.entity.UserRole;
import com.example.mailService.exception.UserAlreadyExistsException;
import com.example.mailService.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AuthControllerTest {

    @Autowired
    private AuthController authController;

    @Autowired
    private UserRepository userRepository;

    private final String USERNAME = "testUser";
    private final String EMAIL = "test@test.com";
    private final String PASSWORD = "test1234";

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

    @BeforeEach
    private void beforeEach() {
        // TODO: @BeforeAll 와 같이 테스트 데이터 setup 을 한번으로 끝낼 수는 없을까?
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        User testUser = User.builder()
                .username(USERNAME)
                .email(EMAIL)
                .password(passwordEncoder.encode(PASSWORD))
                .role(UserRole.ROLE_USER)
                .build();
        userRepository.save(testUser);
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