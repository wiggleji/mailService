package com.example.mailService.domain.dto;

import com.example.mailService.domain.entity.Mail;
import com.example.mailService.domain.entity.MailFile;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MailDto {

    @Builder
    @AllArgsConstructor
    public static class MailInfoList {
        private Long id;

        private String emailFrom;

        private String emailTo;

        private String subject;

        private String content;

        private LocalDateTime dateTimeSend;

        private LocalDateTime dateTimeReceive;

        private List<MailFile> mailFiles = new ArrayList<>();
    }

    @Builder
    @AllArgsConstructor
    public static class MailInfo {
        private Long id;

        private String emailFrom;

        private String emailTo;

        private String subject;

        private String content;

        private LocalDateTime dateTimeSend;

        private LocalDateTime dateTimeReceive;

        private String threadId;

        private List<MailInfo> threadMails = new ArrayList<>();

        private List<MailMetadataDto> metadata = new ArrayList<>();
    }

    @Builder
    @AllArgsConstructor
    public static class MailCreate {
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
