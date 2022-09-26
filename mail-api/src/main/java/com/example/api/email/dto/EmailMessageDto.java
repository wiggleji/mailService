package com.example.api.email.dto;

import lombok.Builder;
import lombok.Getter;

import javax.mail.Address;
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
}
