package com.example.mailService.email;

import com.example.mailService.email.dto.EmailCreateDto;
import com.example.mailService.email.dto.EmailMessageDto;
import com.example.mailService.email.entity.Email;
import com.example.mailService.email.entity.EmailMetadata;
import com.example.mailService.exception.ResourceNotFoundException;
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
import javax.mail.internet.InternetAddress;
import java.util.Optional;
import java.util.Properties;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EmailSendService {
    // 메일 전송 서비스

    private UserService userService;

    private EmailService emailService;

    private EmailMetadataService emailMetadataService;

    private MailSender mailSender;

    public MailSender generateMailSender(EmailMetadata metadata) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", metadata.getSmtpHost());
        properties.put("mail.smtp.port", metadata.getSmtpPort());
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.trust", metadata.getSmtpHost());

        return MailSender.builder()
                .username(metadata.getUsername())
                .password(metadata.getPassword())
                .smtpHost(metadata.getSmtpHost())
                .smtpPort(metadata.getSmtpPort())
                .properties(properties)
                .build();
    }

    public Optional<Message> generateMailSenderMessage(MailSender mailSender, EmailCreateDto createDto) {
        try {
            Session senderSession = mailSender.generateMailSession();
            EmailMessageDto messageDto = createDto.toEmailMessageDto();
            return mailSender.generateMessage(senderSession, messageDto);
        } catch (MessagingException e) {
            log.error("Error while generating MailSender: " + mailSender + " EmailCreateDto: " + createDto);
        }
        return Optional.empty();
    }


    @Transactional(readOnly = false)
    public Email sendEmail(EmailCreateDto createDto) throws MessagingException {
        try {
            // 메일 전송
            // 1. 메일 정보 조회 & 검증
            EmailMetadata emailMetadata = emailMetadataService.loadEmailMetadataByEmailAndUserId(createDto.getEmailFrom(), createDto.getUserId());

            if (validMailMetadata(createDto, emailMetadata)) {
                // 메일 전송 준비
                MailSender mailSender = generateMailSender(emailMetadata);
                // 2. 메일 전송
                Message message = generateMailSenderMessage(mailSender, createDto)
                        .orElseThrow(() -> new MessagingException("Error while generating MailSender Message:" + createDto));
                mailSender.sendMessage(message);
                // 3. 메일 전송 성공 시 Email Entity 저장
                return emailService.createEmail(createDto);
            } else
                throw new IllegalArgumentException("EmailMetadata is not equal to request metadata: " + emailMetadata);
        } catch (ResourceNotFoundException e) {
            // 메일 전송 실패 시 rollback & raise error
            // EmailMetadata 가 존재하지 않으면 rollback
            log.warn("EmailMetadata not found with createDto: " + createDto);
            throw e;
        } catch (MessagingException e) {
            log.warn("MessagingException: " + e);
            throw e;
        }
    }

    public boolean validMailMetadata(EmailCreateDto createDto, EmailMetadata emailMetadata) {
        // 요청자 정보와 메일 정보 검증
        User user = userService.loadUserFromSecurityContextHolder();
        return emailMetadata.getUser().equals(user);
    }
}
