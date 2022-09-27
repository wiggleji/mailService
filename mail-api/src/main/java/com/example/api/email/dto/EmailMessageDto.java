package com.example.api.email.dto;

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
}
