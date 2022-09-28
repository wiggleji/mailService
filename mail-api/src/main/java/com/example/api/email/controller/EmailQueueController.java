package com.example.api.email.controller;

import com.example.api.email.EmailQueueService;
import com.example.api.email.EmailService;
import com.example.api.email.dto.EmailQueueDirectDto;
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
public class EmailQueueController {

    private final EmailService emailService;

    private final EmailQueueService emailQueueService;

    @PostMapping("/queue")
    public ResponseEntity<EmailRequestDto> queueEmail(@RequestBody EmailRequestDto requestDto) {
        EmailRequestDto emailQueueDirectDto = emailQueueService.queueEmail(requestDto);
        return new ResponseEntity<>(emailQueueDirectDto, HttpStatus.CREATED);
    }
}
