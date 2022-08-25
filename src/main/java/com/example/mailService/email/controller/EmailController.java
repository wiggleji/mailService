package com.example.mailService.email.controller;

import com.example.mailService.email.EmailService;
import com.example.mailService.email.dto.EmailDto;
import com.example.mailService.email.entity.Email;
import com.example.mailService.user.UserService;
import com.example.mailService.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
@PreAuthorize("hasRole('ROLE_USER')")
public class EmailController {

    private final UserService userService;

    private final EmailService emailService;

    // get-list
    @GetMapping("/")
    public List<EmailDto> emailList(Authentication authentication) {
        User user = userService.loadUserFromSecurityContextHolder();
        List<Email> emailList = emailService.loadEmailListByUserId(user.getId());
        return emailList.stream().map(EmailDto::from).collect(Collectors.toList());
    }

    // get

    // delete
}
