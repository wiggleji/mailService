package com.example.mailService.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaEmailConsumer {

    @KafkaListener(topics = "email-direct", groupId = "email")
    public void consumeEmailDirectQueueWithHeader(
            @Payload Object message, @Headers MessageHeaders messageHeaders) {
        log.info("direct Email message received: " + message);
    }

    @KafkaListener(topics = "email-schedule", groupId = "email")
    public void consumeEmailScheduleQueueWithHeader(
            @Payload Object message, @Headers MessageHeaders messageHeaders) {
        log.info("schedule Email message received: " + message);
    }
}
