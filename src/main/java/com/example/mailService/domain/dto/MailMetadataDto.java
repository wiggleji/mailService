package com.example.mailService.domain.dto;

import com.example.mailService.domain.entity.CarbonCopy;
import com.example.mailService.domain.entity.Mail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class MailMetadataDto {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class MailMetadataInfoDto {
        private Long id;

        private String name;

        private String email;

        private CarbonCopy carbonCopy;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class MailMetadataCreateDto {
        private Long mailId;

        private String name;

        private String email;

        private CarbonCopy carbonCopy;
    }
}
