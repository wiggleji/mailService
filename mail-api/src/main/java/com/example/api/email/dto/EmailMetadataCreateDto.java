package com.example.api.email.dto;

import com.example.core.entity.user.User;
import com.example.core.entity.email.EmailMetadata;
import com.example.api.utils.Encryption;
import com.example.api.utils.SpringContext;
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
