package com.example.mailService.service;

import com.example.mailService.domain.dto.UserDto;
import com.example.mailService.domain.entity.User;
import com.example.mailService.exception.UserAlreadyExistsException;
import com.example.mailService.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User loadUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    public User loadUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
    }

    public User registerUser(UserDto.UserSignUpDto signUpDto) throws UserAlreadyExistsException {
        Optional<User> existUser = userRepository.findByEmail(signUpDto.getEmail());
        if (existUser.isPresent()) {
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
