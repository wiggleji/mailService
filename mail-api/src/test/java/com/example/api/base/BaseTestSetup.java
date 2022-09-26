package com.example.api.base;

import com.example.core.entity.user.User;
import com.example.core.entity.user.UserRole;
import com.example.core.repository.UserRepository;
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

    protected User compareUser;

    @BeforeEach
    protected void beforeEach() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        testUser = User.builder()
                .username(USERNAME)
                .email(EMAIL)
                .password(passwordEncoder.encode(PASSWORD))
                .role(UserRole.ROLE_USER)
                .build();
        userRepository.save(testUser);
    }

    /**
     * 인증/인가 및 리소스 소유자 검사용 유저 테스트데이터
     */
    protected void createCompareUser() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        compareUser = User.builder()
                .username("compareUser")
                .email("compareUser@test.com")
                .password(passwordEncoder.encode(PASSWORD))
                .role(UserRole.ROLE_USER)
                .build();
        userRepository.save(compareUser);
    }
}
