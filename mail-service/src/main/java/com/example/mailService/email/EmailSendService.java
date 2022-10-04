package com.example.mailService.email;

import com.example.api.email.EmailMetadataWithUserContextService;
import com.example.api.email.EmailWithUserContextService;
import com.example.api.email.dto.EmailMessageDto;
import com.example.api.email.dto.EmailQueueDirectDto;
import com.example.api.email.dto.EmailQueueScheduleDto;
import com.example.core.entity.email.Email;
import com.example.core.entity.email.EmailFolder;
import com.example.core.entity.email.EmailMetadata;
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

    private final EmailWithUserContextService emailWithUserContextService;

    private final EmailMetadataWithUserContextService emailMetadataWithUserContextService;

    private final EmailSender emailSender;

    public Email sendDirectEmail(EmailQueueDirectDto directDto) {
        // 즉시전송: Kafka 에서 받은 EmailQueueDirectDto 로 메일 전송
        try {
            EmailMetadata emailMetadata = emailMetadataWithUserContextService.loadEmailMetadataByEmailAndUserId(directDto.getEmailFrom(), directDto.getUserId());

            sendEmail(emailMetadata, EmailMessageDto.from(directDto));

            return emailWithUserContextService.createEmail(directDto.toEntity(EmailFolder.INBOX));
        } catch (MessagingException e) {
            log.error("Error while sending Email: " + directDto);
            e.getStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Email sendScheduleEmail(EmailQueueScheduleDto scheduleDto) {
        // 예약전송: Email Entity 조회 내역으로 메일 전송
        try {
            Email email = emailWithUserContextService.loadEmailById(scheduleDto.getId());
            EmailMetadata emailMetadata = emailMetadataWithUserContextService.loadEmailMetadataByEmailAndUserId(email.getEmailFrom(), email.getUserId());

            sendEmail(emailMetadata, EmailMessageDto.from(email));

            return emailWithUserContextService.updateEmailToInbox(email);
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
