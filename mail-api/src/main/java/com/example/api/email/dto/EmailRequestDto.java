package com.example.api.email.dto;

import com.example.core.dto.EmailQueueDirectDto;
import com.example.core.entity.email.Email;
import com.example.core.entity.email.EmailFolder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailRequestDto {
    private String emailFrom;

    private String emailTo;

    private String emailToList;

    private String emailCcList;

    private String emailBccList;

    private String subject;

    private String text;

    private LocalDateTime dateTimeSend = LocalDateTime.now();

    private LocalDateTime dateTimeReceive = LocalDateTime.now();

    public EmailQueueDirectDto toEmailQueueDirectDto(Long userId) {
        return EmailQueueDirectDto.builder()
                .userId(userId)
                .emailFrom(this.emailFrom)
                .emailTo(this.emailTo)
                .emailToList(this.emailToList)
                .emailCcList(this.emailCcList)
                .emailBccList(this.emailBccList)
                .subject(this.subject)
                .text(this.text)
                .dateTimeSend(this.dateTimeSend)
                .dateTimeReceive(this.dateTimeReceive)
                .build();
    }

    public Email toScheduledEmailEntity(Long userId) {
        // 예약메일전송 요청 시
        // EmailRequestDto -> Entity (SCHEDULED)
        return Email.builder()
                .userId(userId)
                .emailFolder(EmailFolder.SCHEDULED)
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
