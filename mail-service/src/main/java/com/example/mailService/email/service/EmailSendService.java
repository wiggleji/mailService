package com.example.mailService.email.service;

import com.example.mailService.email.dto.EmailMessageDto;
import com.example.core.dto.EmailQueueDirectDto;
import com.example.core.dto.EmailQueueScheduleDto;
import com.example.core.entity.email.Email;
import com.example.core.entity.email.EmailFolder;
import com.example.core.entity.email.EmailMetadata;
import com.example.mailService.email.EmailSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailSendService {

    private final EmailService emailService;

    private final EmailMetadataService emailMetadataService;

    private final EmailSender emailSender;

    public Email sendDirectEmail(EmailQueueDirectDto directDto) {
        // 즉시전송: Kafka 에서 받은 EmailQueueDirectDto 로 메일 전송
        try {
            EmailMetadata emailMetadata = emailMetadataService.loadEmailMetadataByEmailAndUserId(directDto.getEmailFrom(), directDto.getUserId());

            sendEmail(emailMetadata, EmailMessageDto.from(directDto));

            return emailService.createEmail(directDto.toEntity(EmailFolder.INBOX));
        } catch (MessagingException e) {
            log.error("Error while sending Email: " + directDto);
            e.getStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Email sendScheduleEmail(EmailQueueScheduleDto scheduleDto) {
        // 예약전송: Email Entity 조회 내역으로 메일 전송
        try {
            Email email = emailService.loadEmailById(scheduleDto.getId());
            EmailMetadata emailMetadata = emailMetadataService.loadEmailMetadataByEmailAndUserId(email.getEmailFrom(), email.getUserId());

            sendEmail(emailMetadata, EmailMessageDto.from(email));

            return emailService.updateEmailToInbox(email);
        } catch (MessagingException e) {
            log.error("Error while sending Email: " + scheduleDto);
            e.getStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void sendEmail(EmailMetadata emailMetadata, EmailMessageDto messageDto) throws MessagingException {
        try {
            Session session = emailSender.generateMailSession(emailMetadata);
            Message message = emailSender.generateMessage(session, messageDto);

            emailSender.sendMessage(message);
        } catch (MessagingException e) {
            log.error("Error while sending message from EmailMetadata: " + emailMetadata + " EmailMessageDto: " + messageDto);
            throw e;
        }
    }
}
