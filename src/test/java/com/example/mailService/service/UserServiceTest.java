package com.example.mailService.service;

import com.example.mailService.domain.dto.UserSignUpDto;
import com.example.mailService.domain.entity.User;
import com.example.mailService.exception.UserAlreadyExistsException;
import com.example.mailService.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.transaction.Transactional;

@SpringBootTest
@Transactional
class UserServiceTest {

    private static UserService userService;
    @Autowired
    private UserRepository userRepository;

    private final String USERNAME = "testUser";
    private final String EMAIL = "test@test.com";
    private final String PASSWORD = "test1234";

    @BeforeEach
    private void beforeEach() {
        // TODO: @BeforeAll 와 같이 테스트 데이터 setup 을 한번으로 끝낼 수는 없을까?
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userService = new UserService(userRepository);

        User testUser = User.builder()
                .username(USERNAME)
                .email(EMAIL)
                .password(passwordEncoder.encode(PASSWORD))
                .build();
        userRepository.save(testUser);
    }

    @Test
    public void UserService_loadUserByEmail() throws Exception {
        // given
        String emailNotExists = "no@exist.com";

        // when
        User user = userService.loadUserByEmail(EMAIL);

        // then
        Assertions.assertEquals(user.getEmail(), EMAIL);
        Assertions.assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByEmail(emailNotExists));
    }

    @Test
    public void UserService_loadUserById() throws Exception {
        // given
        Long userId = userRepository.findByEmail(EMAIL).get().getId();

        // when
        User user = userService.loadUserById(userId);

        // then
        Assertions.assertEquals(user.getId(), userId);
        Assertions.assertThrows(UsernameNotFoundException.class, () -> userService.loadUserById(9999999L));
    }
}