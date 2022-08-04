package com.example.mailService.domain.dto;

import com.example.mailService.domain.entity.MailFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MailDto {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class MailInfoListDto {
        private Long id;

        private String emailFrom;

        private String emailTo;

        private String subject;

        private String content;

        private LocalDateTime dateTimeSend;

        private LocalDateTime dateTimeReceive;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class MailInfoDto {
        private Long id;

        private String emailFrom;

        private String emailTo;

        private String subject;

        private String content;

        private LocalDateTime dateTimeSend;

        private LocalDateTime dateTimeReceive;

        private String threadId;

        private List<MailInfoDto> threadMails = new ArrayList<>();

        private List<MailMetadataDto> metadata = new ArrayList<>();
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class MailCreateDto {
        private String emailFrom;

        private String emailTo;

        private String subject;

        private String content;

        private LocalDateTime dateTimeSend;

        private LocalDateTime dateTimeReceive;

        private String threadId;

        private List<String> emailToList = new ArrayList<>();

        private List<String> emailCcList = new ArrayList<>();

        private List<String> emailBccList = new ArrayList<>();
    }
}
