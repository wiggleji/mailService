package com.example.api.user;

import com.example.api.base.BaseTestSetup;
import com.example.core.entity.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.transaction.Transactional;

@SpringBootTest
@Transactional
class UserServiceTest extends BaseTestSetup {

    private final UserService userService;

    @Autowired
    public UserServiceTest(UserService userService) {
        this.userService = userService;
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