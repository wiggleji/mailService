package com.example.mailService.user;

import com.example.mailService.user.dto.JwtTokenDto;
import com.example.mailService.user.dto.UserLoginDto;
import com.example.mailService.user.dto.UserSignUpDto;
import com.example.mailService.user.entity.User;
import com.example.mailService.exception.UserAlreadyExistsException;
import com.example.mailService.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    private final JwtTokenProvider jwtTokenProvider;

    public JwtTokenDto loginUser(UserLoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getUsername(),
                            loginDto.getPassword()
                    )
            );

            // JWT token 발급
            String jwtToken = jwtTokenProvider.generateToken(authentication);
            return JwtTokenDto.builder()
                    .token(jwtToken)
                    .build();

        } catch (AuthenticationException e) {
            log.warn("User Authentication fail: loginDTO {} | {}", loginDto, e.getMessage());
        } return null;
    }

    @Transactional
    public User registerUser(UserSignUpDto signUpDto) throws UserAlreadyExistsException {
        if (userService.checkUserExistsByEmail(signUpDto.getEmail())) {
            throw new UserAlreadyExistsException("User already exists with email: " + signUpDto.getEmail());
        } else {
            return userService.createUser(signUpDto);
        }
    }
}
