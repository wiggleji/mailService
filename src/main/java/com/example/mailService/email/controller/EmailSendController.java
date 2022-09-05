package com.example.mailService.email.controller;

import com.example.mailService.email.EmailSendService;
import com.example.mailService.email.EmailService;
import com.example.mailService.email.dto.EmailCreateDto;
import com.example.mailService.email.dto.EmailDto;
import com.example.mailService.email.entity.Email;
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

    private final EmailSendService emailSendService;

    @PostMapping("/send")
    public ResponseEntity<EmailDto> sendEmail(@RequestBody EmailCreateDto createDto) {
        Email email = emailSendService.sendEmail(createDto);
        return new ResponseEntity<>(EmailDto.from(email), HttpStatus.CREATED);
    }
}
