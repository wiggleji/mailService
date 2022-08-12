package com.example.mailService.controller;

import com.example.mailService.domain.dto.UserDto;
import com.example.mailService.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/self")
    public UserDto userSelfDetail() {
        // TODO: SecurityContextHolder 에서 유저 정보 조회
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return UserDto.from(user);
    }
}
