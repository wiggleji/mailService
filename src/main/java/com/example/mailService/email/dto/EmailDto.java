package com.example.mailService.email.dto;

import com.example.mailService.email.entity.Email;
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

    public static EmailDto from(Email email) {
        return EmailDto.builder()
                .id(email.getId())
                .emailFrom(email.getEmailFrom())
                .emailTo(email.getEmailTo())
                .emailToList(email.getEmailToList())
                .emailCcList(email.getEmailCcList())
                .emailBccList(email.getEmailBccList())
                .subject(email.getSubject())
                .content(email.getContent())
                .dateTimeSend(email.getDateTimeSend())
                .dateTimeReceive(email.getDateTimeReceive())
                .build();
    }

}
