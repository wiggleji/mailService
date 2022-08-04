package com.example.mailService.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;

public class MailFileDto {

    @Builder
    @AllArgsConstructor
    public static class MailFileInfo {
        private Long id;

        private String fileType;

        private String fileUrl;
    }
}
