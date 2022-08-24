package com.example.mailService.service;

import com.example.mailService.user.AuthService;
import com.example.mailService.user.UserService;
import com.example.mailService.user.dto.JwtTokenDto;
import com.example.mailService.user.dto.UserLoginDto;
import com.example.mailService.user.dto.UserSignUpDto;
import com.example.mailService.user.entity.User;
import com.example.mailService.user.entity.UserRole;
import com.example.mailService.exception.UserAlreadyExistsException;
import com.example.mailService.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

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

        userService = new UserService(userRepository);

        User testUser = User.builder()
                .username(USERNAME)
                .email(EMAIL)
                .password(passwordEncoder.encode(PASSWORD))
                .role(UserRole.ROLE_USER)
                .build();
        userRepository.save(testUser);
    }

    @Test
    public void UserService_registerUser_SUCCESS() throws Exception {
        // given
        UserSignUpDto signUpDto = testUserSignUpDto("newUser", "new@test.com");

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
                .username(USERNAME)
                .email(EMAIL)
                .password(PASSWORD)
                .build();

        // when

        // then
        Assertions.assertThrows(UserAlreadyExistsException.class, () -> authService.registerUser(duplicateSignUpDto));
    }

    @Test
    public void UserService_loginUser_SUCCESS() throws Exception {
        // given
        UserLoginDto loginDto = testUserLoginDto("WRONG_USERNAME");

        // when

        // then
        assertNull(authService.loginUser(loginDto));
    }

    @Test
    public void UserService_loginUser_FAIL() throws Exception {
        // given
        UserLoginDto loginDto = testUserLoginDto();

        // when
        JwtTokenDto jwtToken = authService.loginUser(loginDto);

        // then
        assertTrue(StringUtils.hasText(jwtToken.getToken()));
    }
}