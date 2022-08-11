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

	private final UserRepository repository;

	private final PasswordEncoder passwordEncoder;

	public MailServiceApplication(UserRepository repository, PasswordEncoder passwordEncoder) {
		this.repository = repository;
		this.passwordEncoder = passwordEncoder;
	}

	@PostConstruct
	public void initUsers() {
		User user = User.builder()
				.username("admin")
				.email("admin@social.com")
				.password(passwordEncoder.encode("test1234"))
				.role(UserRole.ROLE_ADMIN)
				.build();

		repository.save(user);
	}

	public static void main(String[] args) {
		SpringApplication.run(MailServiceApplication.class, args);
	}

}
