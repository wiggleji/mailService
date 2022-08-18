package com.example.mailService.base;

import com.example.mailService.user.entity.User;
import com.example.mailService.user.entity.UserRole;
import com.example.mailService.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public abstract class BaseTestSetup {

    @Autowired
    protected UserRepository userRepository;

    protected final String USERNAME = "testUser";
    protected final String EMAIL = "test@test.com";
    protected final String PASSWORD = "test1234";

    protected User testUser;

    @BeforeEach
    void beforeEach() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        testUser = User.builder()
                .username(USERNAME)
                .email(EMAIL)
                .password(passwordEncoder.encode(PASSWORD))
                .role(UserRole.ROLE_USER)
                .build();
        userRepository.save(testUser);
    }
}
