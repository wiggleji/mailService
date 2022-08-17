package com.example.mailService.controller;

import com.example.mailService.base.CustomTestSetup;
import com.example.mailService.domain.dto.UserDto;
import com.example.mailService.domain.entity.User;
import com.example.mailService.domain.entity.UserRole;
import com.example.mailService.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserControllerTest extends CustomTestSetup {

    @Autowired
    private UserController userController;

    @Test
    @WithMockUser(username = USERNAME, password = PASSWORD)
    public void testUserControllerUserSelfDetail_SUCCESS() throws Exception {
        // given
        UserDto loginUserDto = userController.userSelfDetail();

        // when

        // then
        Assertions.assertThat(loginUserDto.getUsername()).isEqualTo(USERNAME);
        Assertions.assertThat(loginUserDto.getEmail()).isEqualTo(EMAIL);
    }
}