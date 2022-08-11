package com.example.mailService.controller;

import com.example.mailService.domain.entity.User;
import com.example.mailService.repository.UserRepository;
import com.example.mailService.security.JwtTokenProvider;
import com.example.mailService.service.AuthService;
import com.example.mailService.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AuthControllerTest {
    private static UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    private void beforeEach() {
        // TODO: @BeforeAll 와 같이 테스트 데이터 setup 을 한번으로 끝낼 수는 없을까?
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        userService = new UserService(userRepository);

        User testUser = User.builder()
                .username("testUser")
                .email("test@test.com")
                .password(passwordEncoder.encode("testPassword"))
                .build();
        userRepository.save(testUser);
    }
}