package com.example.mailService.email.dto;

import com.example.mailService.email.entity.EmailMetadata;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class EmailMetadataDto {
    private Long id;

    private String email;

    private String username;

    private String password;

    private String smtpHost;

    private Long smtpPort;

    public static EmailMetadataDto from(EmailMetadata emailMetadata) {
        return EmailMetadataDto.builder()
                .id(emailMetadata.getId())
                .email(emailMetadata.getEmail())
                .password(emailMetadata.getPassword())
                .smtpHost(emailMetadata.getSmtpHost())
                .smtpPort(emailMetadata.getSmtpPort())
                .build();
    }

    public static List<EmailMetadataDto> from(List<EmailMetadata> emailMetadata) {
        return emailMetadata.stream()
                .map(EmailMetadataDto::from)
                .collect(Collectors.toList());
    }
}
