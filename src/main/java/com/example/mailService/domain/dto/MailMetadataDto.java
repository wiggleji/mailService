package com.example.mailService.domain.dto;

import com.example.mailService.domain.entity.CarbonCopy;
import com.example.mailService.domain.entity.Mail;
import lombok.AllArgsConstructor;
import lombok.Builder;

public class MailMetadataDto {

    @Builder
    @AllArgsConstructor
    public static class MailMetadataInfo {
        private Long id;

        private String name;

        private String email;

        private CarbonCopy carbonCopy;
    }

    @Builder
    @AllArgsConstructor
    public static class MailMetadataCreate {
        private Long mailId;

        private String name;

        private String email;

        private CarbonCopy carbonCopy;
    }
}
