package com.example.mailService.email.controller;

import com.example.mailService.email.EmailService;
import com.example.mailService.email.dto.EmailDto;
import com.example.mailService.email.entity.Email;
import com.example.mailService.user.UserService;
import com.example.mailService.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
@PreAuthorize("hasRole('ROLE_USER')")
public class EmailController {

    private final UserService userService;

    private final EmailService emailService;

    @GetMapping("/")
    public List<EmailDto> emailList() {
        List<Email> emailList = emailService.loadEmailListByUserId();
        return emailList.stream().map(EmailDto::from).collect(Collectors.toList());
    }

    @GetMapping("/{emailId}")
    public EmailDto emailDetail(@PathVariable Long emailId) {
        Email email = emailService.loadEmailByIdAndUserId(emailId);
        return EmailDto.from(email);
    }

    @DeleteMapping("/{emailId}")
    public void emailDelete(@PathVariable Long emailId) {
        // TODO: soft delete 적용 후 메일 삭제 로직 & 테스트케이스 작성
        emailService.deleteEmail(emailId);
    }
}
