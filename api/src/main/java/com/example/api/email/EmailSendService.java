package com.example.api.email;

import com.example.api.email.dto.EmailCreateDto;
import com.example.core.entity.email.Email;
import com.example.core.entity.email.EmailMetadata;
import com.example.api.user.UserService;
import com.example.core.entity.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;


@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EmailSendService {
    // 메일 전송 서비스

    private final UserService userService;

    private final EmailService emailService;

    private final EmailMetadataService emailMetadataService;

    private final MailSender mailSender;



    @Transactional(readOnly = false)
    public Email sendEmail(EmailCreateDto createDto) {
        try {
            // 메일 전송
            // 1. 메일 정보 조회 & 검증
            emailMetadataService.validMailMetadata(createDto);

            // 2. 메일 전송
            sendMailByEmailCreateDto(createDto);

            // 3. 메일 전송 후 Email Entity 저장
            return emailService.createEmail(createDto);

        } catch (MessagingException e) {
            log.error("Error while sending Email: " + createDto);
            e.getStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Transactional(readOnly = false)
    public void sendMailByEmailCreateDto(EmailCreateDto createDto) throws MessagingException {
        // User & EmailMetadata 조회
        User requestUser = userService.loadUserFromSecurityContextHolder();
        EmailMetadata metadata = emailMetadataService.loadEmailMetadataByEmailAndUserId(createDto.getEmailFrom(), requestUser.getId());

        try {
            // Java Mail API session/message 생성
            Session session = mailSender.generateMailSession(metadata);
            Message message = mailSender.generateMessage(session, createDto.toEmailMessageDto());

            // 메일 전송
            mailSender.sendMessage(message);

        } catch (MessagingException e) {
            log.error("Error while sending message from EmailCreateDto: " + createDto);
            throw e;
        }
    }
}
