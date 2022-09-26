package com.example.api.user.controller;

import com.example.api.user.UserService;
import com.example.api.user.dto.UserDto;
import com.example.core.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/self")
    public UserDto userSelfDetail() {
        // TODO: SecurityContextHolder 에서 유저 정보 조회
        // https://docs.spring.io/spring-security/site/docs/4.2.x/reference/html/test-method.html
        User user = userService.loadUserFromSecurityContextHolder();
        return UserDto.from(user);
    }
}
