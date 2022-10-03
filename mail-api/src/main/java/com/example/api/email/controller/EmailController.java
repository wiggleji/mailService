package com.example.api.email.controller;

import com.example.api.email.EmailService;
import com.example.api.email.dto.EmailDto;
import com.example.core.entity.email.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
public class EmailController {

    private final EmailService emailService;

    @GetMapping("/")
    public ResponseEntity<List<EmailDto>> emailList() {
        List<Email> emailList = emailService.loadEmailListByUserId();
        if (emailList.size() > 0)
            return new ResponseEntity<>(EmailDto.from(emailList), HttpStatus.OK);
        else return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{emailId}")
    public ResponseEntity<EmailDto> emailDetail(@PathVariable Long emailId) {
        Email email = emailService.loadEmailByIdAndUserId(emailId);
        return new ResponseEntity<>(EmailDto.from(email), HttpStatus.OK);
    }
}
