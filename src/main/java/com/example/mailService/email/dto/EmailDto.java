package com.example.mailService.email.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
@Builder
public class EmailDto {

    private Long id;

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
