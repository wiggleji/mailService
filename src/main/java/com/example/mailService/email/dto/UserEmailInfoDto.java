package com.example.mailService.email.dto;

import com.example.mailService.email.entity.UserEmailInfo;
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

    public static UserEmailInfoDto from(UserEmailInfo userEmailInfo) {
        return UserEmailInfoDto.builder()
                .id(userEmailInfo.getId())
                .email(userEmailInfo.getEmail())
                .password(userEmailInfo.getPassword())
                .smtpHost(userEmailInfo.getSmtpHost())
                .smtpPort(userEmailInfo.getSmtpPort())
                .build();
    }
}
