package com.example.mailService.email.dto;

import com.example.mailService.email.entity.Email;
import lombok.Builder;
import lombok.Getter;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import java.time.LocalDateTime;

@Getter
@Builder
public class EmailCreateDto {

    private String emailFrom;

    private String emailTo;

    private String emailToList;

    private String emailCcList;

    private String emailBccList;

    private String subject;

    private String text;

    private LocalDateTime dateTimeSend;

    private LocalDateTime dateTimeReceive;

    public Email toEntity(Long userId) {
        return Email.builder()
                .userId(userId)
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

    public EmailMessageDto toEmailMessageDto() throws MessagingException {
        Address[] addressesTo = InternetAddress.parse(getEmailToList());
        Address[] addressesCc = InternetAddress.parse(getEmailCcList());
        Address[] addressesBcc = InternetAddress.parse(getEmailBccList());

        return EmailMessageDto.builder()
                .addressFrom(new InternetAddress(getEmailFrom()))
                .addressTo(addressesTo)
                .addressCc(addressesCc)
                .addressBcc(addressesBcc)
                .subject(getSubject())
                .text(getText())
                .build();
    }
}
