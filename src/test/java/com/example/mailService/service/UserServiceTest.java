package com.example.mailService.service;

import com.example.mailService.base.CustomTestSetup;
import com.example.mailService.domain.dto.UserSignUpDto;
import com.example.mailService.domain.entity.User;
import com.example.mailService.domain.entity.UserRole;
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
class UserServiceTest extends CustomTestSetup {

    @Autowired
    private UserService userService;

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