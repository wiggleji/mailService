package com.example.api.email.controller;

import com.example.api.email.EmailQueueService;
import com.example.api.email.EmailService;
import com.example.api.email.dto.EmailRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequiredArgsConstructor
@RequestMapping("/email-send")
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
public class EmailSendController {

    private final EmailService emailService;

    private final EmailQueueService emailQueueService;

    @PostMapping("/queue")
    public ResponseEntity<EmailRequestDto> sendEmail(@RequestBody EmailRequestDto requestDto) {
//        Email email = emailSendService.sendEmail(createDto);
        EmailRequestDto queueEmail = emailQueueService.queueEmail(requestDto);
        return new ResponseEntity<>(queueEmail, HttpStatus.CREATED);
    }
}
