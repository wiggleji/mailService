package com.example.mailService.domain.dto;

import com.example.mailService.domain.entity.Folder;
import com.example.mailService.domain.entity.Mail;
import com.example.mailService.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;

public class UserMailDto {

    @Builder
    @AllArgsConstructor
    public static class UserMailInfo {
        private User user;

        private Mail mail;

        private Folder folderId;
    }
}
