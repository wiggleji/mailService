package com.example.mailService.email;

import com.example.mailService.base.BaseTestSetup;
import com.example.mailService.email.dto.EmailCreateDto;
import com.example.mailService.email.entity.Email;
import com.example.mailService.email.entity.EmailMetadata;
import com.example.mailService.user.entity.User;

public class EmailTestSetup extends BaseTestSetup {

    public Email testEmail(String subject, User user) {
        return Email.builder()
                .userId(user.getId())
                .emailFrom(user.getEmail())
                .emailTo("otherUser@to.com")
                .emailToList("otherUser@to.com")
                .emailCcList("cc1@otherMail.com, cc2@otherMail.com")
                .emailBccList("bcc1@otherMail.com, bcc2@otherMail.com")
                .subject(subject)
                .text("test email text")
                .build();
    }

    public EmailMetadata testEmailMetadata(User user) {
        return EmailMetadata.builder()
                .email("testUser@testMail.com")
                .username("testUser")
                .password("testPassword")
                .smtpHost("smtp.testMail.com")
                .smtpPort(465L)
                .user(user)
                .build();
    }

    public EmailCreateDto testEmailCreateDto(String from, String to, String subject, String ccList, String bccList, Long userId) {
        return EmailCreateDto.builder()
                .emailFrom(from)
                .emailTo(to)
                .subject(subject)
                .text("Test Mail content")
                .emailToList(to)
                .emailCcList(ccList)
                .emailBccList(bccList)
                .userId(userId)
                .build();
    }

    public EmailCreateDto testEmailCreateDto__NoCcBcc(String from, String to, String subject, Long userId) {
        return EmailCreateDto.builder()
                .emailFrom(from)
                .emailTo(to)
                .subject(subject)
                .text("Test Mail content")
                .userId(userId)
                .build();
    }
}
