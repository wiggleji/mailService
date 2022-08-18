package com.example.mailService.user.controller;

import com.example.mailService.user.dto.JwtTokenDto;
import com.example.mailService.user.dto.UserDto;
import com.example.mailService.user.dto.UserLoginDto;
import com.example.mailService.user.dto.UserSignUpDto;
import com.example.mailService.user.entity.User;
import com.example.mailService.user.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@Valid @RequestBody UserLoginDto loginDto) {
        JwtTokenDto tokenDto = authService.loginUser(loginDto);

        if (Objects.isNull(tokenDto)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Authorization header 에 JWT 토큰 설정
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + tokenDto.getToken());

        return new ResponseEntity<>(tokenDto, headers, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public UserDto userSignUp(@Valid @RequestBody UserSignUpDto signUpDto) {
        User user = authService.registerUser(signUpDto);
        return UserDto.from(user);
    }
}
