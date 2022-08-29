package com.example.mailService.email.dto;

import com.example.mailService.user.entity.User;
import com.example.mailService.email.entity.EmailMetadata;
import com.example.mailService.utils.Encryption;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
                .password(Encryption.encryptAES256(password))
                .smtpHost(smtpHost)
                .smtpPort(smtpPort)
                .user(user)
                .build();
    }
}
