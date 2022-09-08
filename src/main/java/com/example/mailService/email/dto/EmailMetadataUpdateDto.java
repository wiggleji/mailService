package com.example.mailService.email.dto;

import com.example.mailService.email.entity.EmailMetadata;
import com.example.mailService.user.entity.User;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@Builder
public class EmailMetadataUpdateDto {
    private String email;

    private String username;

    private String password;

    private String smtpHost;

    private Long smtpPort;
}
