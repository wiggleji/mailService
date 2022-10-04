package com.example.mailService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Collections;

@EntityScan({"com.example.core"})
@ComponentScan(basePackages = {"com.example.api.email"})
@EnableJpaRepositories({"com.example.core"})
@SpringBootApplication
public class MailServiceApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MailServiceApplication.class);
        // application properties 에서 port 를 읽어오지 못해 강제 설정
//        app.setDefaultProperties(CollectionsZ
        app.run(args);
    }
}
