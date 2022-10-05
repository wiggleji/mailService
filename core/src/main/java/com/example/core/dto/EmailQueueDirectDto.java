package com.example.core.dto;

import com.example.core.entity.email.Email;
import com.example.core.entity.email.EmailFolder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailQueueDirectDto {

    private Long userId;

    private String emailFrom;

    private String emailTo;

    private String emailToList;

    private String emailCcList;

    private String emailBccList;

    private String subject;

    private String text;

    private LocalDateTime dateTimeSend = LocalDateTime.now().withSecond(0).withNano(0);

    private LocalDateTime dateTimeReceive = LocalDateTime.now();

    public String getStringUuid() {
        return (this.userId.toString() + "--" + dateTimeSend.toInstant(ZoneOffset.ofTotalSeconds(0)).toString());
    }

    public Email toEntity(EmailFolder emailFolder) {
        return Email.builder()
                .userId(userId)
                .emailFolder(emailFolder)
                .emailFrom(emailFrom)
                .emailTo(emailTo)
                .emailToList(emailToList)
                .emailCcList(emailCcList)
                .emailBccList(emailBccList)
                .subject(subject)
                .text(text)
                .dateTimeSend(dateTimeSend)
                .dateTimeReceive(dateTimeReceive)
                .build();
    }
}
