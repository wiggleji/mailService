package com.example.mailService.email.controller;

import com.example.mailService.email.EmailSendService;
import com.example.mailService.email.EmailService;
import com.example.mailService.email.dto.EmailCreateDto;
import com.example.mailService.email.dto.EmailDto;
import com.example.mailService.email.entity.Email;
import com.example.mailService.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
public class EmailController {

    private final EmailService emailService;

    private final EmailSendService emailSendService;

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
