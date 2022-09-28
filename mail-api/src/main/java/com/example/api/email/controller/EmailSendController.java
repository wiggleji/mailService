package com.example.api.email.controller;

import com.example.api.email.EmailSendService;
import com.example.api.email.EmailService;
import com.example.api.email.dto.EmailCreateDto;
import com.example.api.email.dto.EmailDto;
import com.example.core.entity.email.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Deprecated
@RestController
@RequiredArgsConstructor
@RequestMapping("/email-depreated")
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
