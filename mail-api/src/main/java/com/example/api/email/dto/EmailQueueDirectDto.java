package com.example.api.email.dto;

import com.example.core.entity.email.Email;
import com.example.core.entity.email.EmailFolder;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.StringUtils;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Getter
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

    private LocalDateTime dateTimeSend = LocalDateTime.now();

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
