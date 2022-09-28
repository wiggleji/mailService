package com.example.mailService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Collections;

@SpringBootApplication
@EnableJpaAuditing
@EntityScan(basePackages = {"com.example.core"})
@EnableJpaRepositories(basePackages = {"com.example.core"})
public class MailServiceApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MailServiceApplication.class);
        app.setDefaultProperties(Collections
                .singletonMap("server.port", "8090"));
        app.run(args);
    }
}
