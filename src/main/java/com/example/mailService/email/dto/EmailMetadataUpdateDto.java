package com.example.mailService.email.dto;

import com.example.mailService.email.entity.EmailMetadata;
import com.example.mailService.user.entity.User;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@Builder
public class EmailMetadataUpdateDto {
    @NotNull
    private String email;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String smtpHost;

    @NotNull
    private Long smtpPort;
}
