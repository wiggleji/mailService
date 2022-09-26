package com.example.api.email.dto;

import com.example.api.utils.Encryption;
import com.example.api.utils.SpringContext;
import com.example.core.entity.email.EmailMetadata;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmailMetadataUpdateDto {
    private String email;

    private String username;

    private String password;

    private String smtpHost;

    private Long smtpPort;

    public EmailMetadata toUpdateEntity(Long emailMetadataId) {
        Encryption encryption = SpringContext.getBean(Encryption.class);

        return EmailMetadata.builder()
                .id(emailMetadataId)
                .email(getEmail())
                .username(getUsername())
                .password(encryption.encryptAES256(getPassword()))
                .smtpHost(getSmtpHost())
                .smtpPort(getSmtpPort())
                .build();
    }
}
