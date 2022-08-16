package com.example.mailService.domain.dto;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
@Builder
public class EmailCreateDto {

    private Long userId;

    private String emailFrom;

    private String emailTo;

    private String emailToList;

    private String emailCcList;

    private String emailBccList;

    private String subject;

    private String content;

    private LocalDateTime dateTimeSend;

    private LocalDateTime dateTimeReceive;
}
