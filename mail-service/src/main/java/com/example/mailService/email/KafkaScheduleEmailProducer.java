package com.example.mailService.email;

import com.example.core.dto.EmailQueueScheduleDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Component
public class KafkaScheduleEmailProducer {

    private final KafkaTemplate<String, EmailQueueScheduleDto> kafkaTemplate;

    @Autowired
    public KafkaScheduleEmailProducer(KafkaTemplate<String, EmailQueueScheduleDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topicName, String messageKey, Object message) {

        Message<Object> messageData = MessageBuilder
                .withPayload(message)
                .setHeader(KafkaHeaders.TOPIC, topicName)
                .setHeader(KafkaHeaders.MESSAGE_KEY, messageKey)
                .build();

        ListenableFuture<SendResult<String, EmailQueueScheduleDto>> future = kafkaTemplate.send(messageData);

        future.addCallback(
                new ListenableFutureCallback<SendResult<String, ?>>() {
                    @Override
                    public void onFailure(Throwable ex) {
                        log.warn("[KafkaScheduleEmailProducer] Unable to send message: [" + messageData + "] due to: " + ex.getMessage());
                    }

                    @Override
                    public void onSuccess(SendResult<String, ?> result) {
                        log.info("[KafkaScheduleEmailProducer] Succeed to send message: [" + messageData + "] | offset: [" + result.getRecordMetadata().offset() + "]");
                    }
                }
        );
    }
}
