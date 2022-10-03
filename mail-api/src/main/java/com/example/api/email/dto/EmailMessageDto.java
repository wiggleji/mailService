package com.example.api.email.dto;

import com.example.core.entity.email.Email;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.StringUtils;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

@Getter
@Builder
public class EmailMessageDto {

    private InternetAddress addressFrom;

    private Address[] addressTo;
    private Address[] addressCc;
    private Address[] addressBcc;

    private String subject;

    private String text;

    public static EmailMessageDto from(EmailQueueDirectDto directDto) throws MessagingException {
        return getEmailMessageDto(
                directDto.getEmailToList(),
                directDto.getEmailCcList(),
                directDto.getEmailBccList(),
                directDto.getEmailFrom(),
                directDto.getSubject(),
                directDto.getText());
    }

    public static EmailMessageDto from(Email email) throws MessagingException {
        return getEmailMessageDto(
                email.getEmailToList(),
                email.getEmailCcList(),
                email.getEmailBccList(),
                email.getEmailFrom(),
                email.getSubject(),
                email.getText());
    }

    private static EmailMessageDto getEmailMessageDto(String emailToList, String emailCcList, String emailBccList, String emailFrom, String subject, String text) throws AddressException {
        Address[] addressesTo = parseValidateInternetAddressList(emailToList);
        Address[] addressesCc = parseValidateInternetAddressList(emailCcList);
        Address[] addressesBcc = parseValidateInternetAddressList(emailBccList);

        return EmailMessageDto.builder()
                .addressFrom(new InternetAddress(emailFrom))
                .addressTo(addressesTo)
                .addressCc(addressesCc)
                .addressBcc(addressesBcc)
                .subject(subject)
                .text(text)
                .build();
    }

    protected static InternetAddress[] parseValidateInternetAddressList(String email) {
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
}
