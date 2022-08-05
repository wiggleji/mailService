package com.example.mailService.service;

import com.example.mailService.domain.dto.UserDto.UserSignUpDto;
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
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    private static UserService userService;
    @Autowired private UserRepository userRepository;

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

    @Test
    public void UserService_loadUserByEmail() throws Exception {
        // given
        String email = "test@test.com";
        String emailNotExists = "no@exist.com";

        // when
        User user = userService.loadUserByEmail(email);

        // then
        Assertions.assertEquals(user.getEmail(), email);
        Assertions.assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByEmail(emailNotExists));
    }

    @Test
    public void UserService_loadUserById() throws Exception {
        // given
        Long userId = userRepository.findByEmail("test@test.com").get().getId();

        // when
        User user = userService.loadUserById(userId);

        // then
        Assertions.assertEquals(user.getId(), userId);
        Assertions.assertThrows(UsernameNotFoundException.class, () -> userService.loadUserById(9999999L));
    }

    @Test
    public void UserService_registerUser_SUCCESS() throws Exception {
        // given
        UserSignUpDto signUpDto = UserSignUpDto.builder()
                .username("signUpUser")
                .email("signup@test.com")
                .password("testPassword")
                .build();

        // when
        User signUpUser = userService.registerUser(signUpDto);

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
        Assertions.assertThrows(UserAlreadyExistsException.class, () -> userService.registerUser(duplicateSignUpDto));
    }
}