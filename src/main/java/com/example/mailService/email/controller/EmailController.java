package com.example.mailService.email.controller;

import com.example.mailService.email.EmailService;
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
@PreAuthorize("hasRole('ROLE_USER')")
public class EmailController {

    private final UserService userService;

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
        Optional<Email> email = emailService.loadEmailByIdAndUserId(emailId);
        return email.map(value -> new ResponseEntity<>(EmailDto.from(value), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{emailId}")
    public void emailDelete(@PathVariable Long emailId) {
        // TODO: soft delete 적용 후 메일 삭제 로직 & 테스트케이스 작성
        emailService.deleteEmail(emailId);
    }
}
