package com.example.mailService.email.dto;

import com.example.mailService.email.entity.Email;
import lombok.Builder;
import lombok.Getter;

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

    public Email toEntity() {
        return Email.builder()
                .userId(userId)
                .emailFrom(emailFrom)
                .emailTo(emailTo)
                .emailToList(emailToList)
                .emailCcList(emailCcList)
                .emailBccList(emailBccList)
                .subject(subject)
                .content(content)
                .dateTimeSend(dateTimeSend)
                .dateTimeReceive(dateTimeReceive)
                .build();
    }
}
