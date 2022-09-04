package com.example.mailService.email.dto;

import com.example.mailService.user.entity.User;
import com.example.mailService.email.entity.EmailMetadata;
import com.example.mailService.utils.Encryption;
import com.example.mailService.SpringContext;
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
        Encryption encryption = SpringContext.getBean(Encryption.class);

        return EmailMetadata.builder()
                .email(email)
                .username(username)
                .password(encryption.encryptAES256(password))
                .smtpHost(smtpHost)
                .smtpPort(smtpPort)
                .user(user)
                .build();
    }
}
