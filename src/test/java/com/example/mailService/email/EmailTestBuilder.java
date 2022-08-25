package com.example.mailService.email;

import com.example.mailService.email.dto.EmailCreateDto;

public interface EmailTestBuilder {

    public default EmailCreateDto testEmailCreateDto(String from, String to, String subject, String ccList, String bccList, Long userId) {
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

    public default EmailCreateDto testEmailCreateDto__NoCcBcc(String from, String to, String subject, Long userId) {
        return EmailCreateDto.builder()
                .emailFrom(from)
                .emailTo(to)
                .subject(subject)
                .text("Test Mail content")
                .userId(userId)
                .build();
    }
}
