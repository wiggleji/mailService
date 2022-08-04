package com.example.mailService.domain.dto;

import com.example.mailService.domain.entity.Folder;
import com.example.mailService.domain.entity.Mail;
import com.example.mailService.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class UserMailDto {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class UserMailInfoDto {
        private User user;

        private Mail mail;

        private Folder folderId;
    }
}
