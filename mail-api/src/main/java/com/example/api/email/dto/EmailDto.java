package com.example.api.email.dto;

import com.example.core.entity.email.Email;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    private String text;

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
                .text(email.getText())
                .dateTimeSend(email.getDateTimeSend())
                .dateTimeReceive(email.getDateTimeReceive())
                .build();
    }

    public static List<EmailDto> from(List<Email> emailList) {
        return emailList.stream()
                .map(EmailDto::from)
                .collect(Collectors.toList());
    }
}
