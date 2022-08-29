package com.example.mailService.email.dto;

import com.example.mailService.user.entity.User;
import com.example.mailService.email.entity.EmailMetadata;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmailMetadataCreateDto {

    private String email;

    private String username;

    private String password;

    private String smtpHost;

    private Long smtpPort;

    public EmailMetadata toEntity(User user) {
        return EmailMetadata.builder()
                .email(email)
                .username(username)
                .password(password)
                .smtpHost(smtpHost)
                .smtpPort(smtpPort)
                .user(user)
                .build();
    }
}
