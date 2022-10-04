package com.example.api;

import com.example.core.repository.UserRepository;
import com.example.core.entity.user.User;
import com.example.core.entity.user.UserRole;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableJpaAuditing
@EntityScan({"com.example.core"})
@EnableJpaRepositories({"com.example.core"})
public class ApiApplication {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public ApiApplication(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void initUsers() {
        userRepository.findByUsername("admin").orElseGet(() -> {
            User user = User.builder()
                    .username("admin")
                    .email("admin@social.com")
                    .password(passwordEncoder.encode("test1234"))
                    .role(UserRole.ROLE_ADMIN)
                    .build();

            userRepository.save(user);
            return user;
        });
    }

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }
}