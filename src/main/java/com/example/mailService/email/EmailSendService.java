package com.example.mailService.email;

import com.example.mailService.email.dto.EmailCreateDto;
import com.example.mailService.email.entity.Email;
import com.example.mailService.utils.MailSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;


@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EmailSendService {
    // 메일 전송 서비스

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
                mailSender.sendMailByEmailCreateDto(createDto);

                // 3. 메일 전송 후 Email Entity 저장
                return emailService.createEmail(createDto);

            } else throw new IllegalArgumentException("EmailMetadata is not equal to request metadata: " + createDto);
        } catch (MessagingException e) {
            log.error("Error while sending Email: " + createDto);
            e.getStackTrace();
            throw new RuntimeException(e);
        }
    }
}
