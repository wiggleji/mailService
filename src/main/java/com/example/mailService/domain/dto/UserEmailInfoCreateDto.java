package com.example.mailService.domain.dto;

import com.example.mailService.domain.entity.User;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Builder
public class UserEmailInfoCreateDto {

    private String email;

    private String username;

    private String password;

    private String smtpHost;

    private Long smtpPort;

    private User user;
}
