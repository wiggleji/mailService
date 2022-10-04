package com.example.mailService.email;

import com.example.api.email.dto.EmailQueueDirectDto;
import com.example.api.email.dto.EmailQueueScheduleDto;
import com.example.core.entity.email.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaEmailConsumer {

    private final EmailSendService emailSendService;

    @KafkaListener(topics = "email-direct", groupId = "email")
    public void consumeEmailDirectQueueWithHeader(
            @Payload Object message, @Headers MessageHeaders messageHeaders) {
        log.info("direct Email message received: " + message);

        Email email = emailSendService.sendDirectEmail((EmailQueueDirectDto) message);
        log.info("direct Email: " + email + " was sent. " + email.getDateTimeSend());
    }

    @KafkaListener(topics = "email-schedule", groupId = "email")
    public void consumeEmailScheduleQueueWithHeader(
            @Payload Object message, @Headers MessageHeaders messageHeaders) {
        log.info("schedule Email message received: " + message);

        Email email = emailSendService.sendScheduleEmail((EmailQueueScheduleDto) message);
        log.info("schedule Email: " + email + " was sent. " + email.getDateTimeSend());
    }
}
