package com.example.mailService.service;

import com.example.mailService.utils.MailUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSendService {

    private EmailService emailService;

    private MailUtils mailUtils;

    // 메일 전송 기능
    // 1. 유저 메일정보 조회 & 검증
    // 2. DTO를 기반으로 메일 작성
}
