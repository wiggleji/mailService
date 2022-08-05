package com.example.mailService.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class MailFileDto {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class MailFileInfoDto {
        private Long id;

        private String fileType;

        private String fileUrl;
    }
}
