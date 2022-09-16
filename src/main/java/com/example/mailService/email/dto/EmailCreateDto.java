package com.example.mailService.email.dto;

import com.example.mailService.email.entity.Email;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.StringUtils;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.time.LocalDateTime;
import java.util.Arrays;

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

    private LocalDateTime dateTimeSend = LocalDateTime.now();

    private LocalDateTime dateTimeReceive = LocalDateTime.now();

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

    protected InternetAddress[] parseValidateInternetAddressList(String email) {
        try {
            if (StringUtils.hasText(email)) {
                InternetAddress[] addresses = InternetAddress.parse(email);
                for (InternetAddress address: addresses) {
                    address.validate();
                }
                return addresses;
            } else return null;
        } catch (AddressException e) {
            throw new IllegalArgumentException("Wrong email to parse: " + email);
        }
    }

    public EmailMessageDto toEmailMessageDto() throws MessagingException {
        Address[] addressesTo = parseValidateInternetAddressList(getEmailToList());
        Address[] addressesCc = parseValidateInternetAddressList(getEmailCcList());
        Address[] addressesBcc = parseValidateInternetAddressList(getEmailBccList());

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
