package com.example.mailService.user.controller;

import com.example.mailService.base.BaseTestSetup;
import com.example.mailService.user.controller.UserController;
import com.example.mailService.user.dto.UserDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class UserControllerTest extends BaseTestSetup {

    private final UserController userController;

    @Autowired
    public UserControllerTest(UserController userController) {
        this.userController = userController;
    }

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
