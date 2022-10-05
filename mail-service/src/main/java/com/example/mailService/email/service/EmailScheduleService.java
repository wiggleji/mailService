package com.example.mailService.email.service;

import com.example.core.dto.EmailQueueScheduleDto;
import com.example.core.entity.email.Email;
import com.example.mailService.email.KafkaScheduleEmailProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class EmailScheduleService {

    private final KafkaScheduleEmailProducer kafkaScheduleEmailProducer;

    private final EmailService emailService;

    @Scheduled(cron = "0 * * * * *")
    public void queueScheduleEmail() {
        LocalDateTime dateTimeNow = LocalDateTime.now().withSecond(0).withNano(0);
        log.info("Email Schedule start: " + dateTimeNow);

        List<Email> scheduledEmails = emailService.findScheduledEmailByDatetimeSend(dateTimeNow);
        for (Email email : scheduledEmails) {
            EmailQueueScheduleDto scheduleDto = EmailQueueScheduleDto.from(email);
            kafkaScheduleEmailProducer.sendMessage("email-schedule", scheduleDto.getStringUuid(), scheduleDto);
        }
    }
}
