package com.example.mailService.service;

import com.example.mailService.domain.dto.JwtTokenDto;
import com.example.mailService.domain.dto.UserLoginDto;
import com.example.mailService.domain.dto.UserSignUpDto;
import com.example.mailService.domain.entity.User;
import com.example.mailService.exception.UserAlreadyExistsException;
import com.example.mailService.repository.UserRepository;
import com.example.mailService.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    private final JwtTokenProvider jwtTokenProvider;

    public JwtTokenDto loginUser(UserLoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getUsername(),
                            loginDto.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // JWT token 발급
            String jwtToken = jwtTokenProvider.generateToken(authentication);
            return JwtTokenDto.builder()
                    .token(jwtToken)
                    .build();

        } catch (AuthenticationException e) {
            log.warn("User Authentication fail: loginDTO {} | {}", loginDto, e.getMessage());
        } return null;
    }

    public User registerUser(UserSignUpDto signUpDto) throws UserAlreadyExistsException {

        Optional<User> existingUser = userRepository.findByEmail(signUpDto.getEmail());
        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException("User already exists with email: " + signUpDto.getEmail());
        } else {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            User newUser = User.builder()
                    .username(signUpDto.getUsername())
                    .email(signUpDto.getEmail())
                    .password(passwordEncoder.encode(signUpDto.getPassword()))
                    .build();

            return userRepository.save(newUser);
        }
    }
}
