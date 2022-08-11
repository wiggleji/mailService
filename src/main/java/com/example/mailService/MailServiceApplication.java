package com.example.mailService;

import com.example.mailService.domain.entity.User;
import com.example.mailService.domain.entity.UserRole;
import com.example.mailService.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class MailServiceApplication {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	public MailServiceApplication(UserRepository userRepository, PasswordEncoder passwordEncoder) {
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
		SpringApplication.run(MailServiceApplication.class, args);
	}

}
