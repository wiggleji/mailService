package com.example.mailService.email;

import com.example.mailService.email.dto.EmailCreateDto;
import com.example.mailService.email.entity.Email;
import com.example.mailService.email.entity.EmailMetadata;
import com.example.mailService.user.UserService;
import com.example.mailService.user.entity.User;
import com.example.mailService.utils.MailSender;
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
            if (emailMetadataService.validMailMetadata(createDto)) {

                // 2. 메일 전송
                sendMailByEmailCreateDto(createDto);

                // 3. 메일 전송 후 Email Entity 저장
                return emailService.createEmail(createDto);

            } else throw new IllegalArgumentException("EmailMetadata is not equal to request metadata: " + createDto);
        } catch (MessagingException e) {
            log.error("Error while sending Email: " + createDto);
            e.getStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void sendMailByEmailCreateDto(EmailCreateDto createDto) throws MessagingException {
        try {
            User requestUser = userService.loadUserFromSecurityContextHolder();
            // EmailCreateDto 로부터 metadata 조회
            EmailMetadata metadata = emailMetadataService.loadEmailMetadataByEmailAndUserId(createDto.getEmailFrom(), requestUser.getId());
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
