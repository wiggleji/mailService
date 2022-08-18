package com.example.mailService.domain.dto;

import com.example.mailService.domain.entity.User;
import com.example.mailService.domain.entity.UserEmailInfo;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
