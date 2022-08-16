package com.example.mailService.service;

import com.example.mailService.domain.dto.UserSignUpDto;
import com.example.mailService.domain.entity.User;
import com.example.mailService.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User loadUserByEmail(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    public boolean checkUserExistsByEmail(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        return optionalUser.isPresent();
    }

    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    public User loadUserById(Long id) throws UsernameNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
    }

    public User createUser(UserSignUpDto signUpDto) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        User newUser = User.builder()
                .username(signUpDto.getUsername())
                .email(signUpDto.getEmail())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .build();

        return userRepository.save(newUser);
    }
}
