package com.example.mailService.email.dto;

import com.example.mailService.email.entity.EmailMetadata;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserEmailInfoDto {
    private Long id;

    private String email;

    private String username;

    private String password;

    private String smtpHost;

    private Long smtpPort;

    public static UserEmailInfoDto from(EmailMetadata emailMetadata) {
        return UserEmailInfoDto.builder()
                .id(emailMetadata.getId())
                .email(emailMetadata.getEmail())
                .password(emailMetadata.getPassword())
                .smtpHost(emailMetadata.getSmtpHost())
                .smtpPort(emailMetadata.getSmtpPort())
                .build();
    }
}
