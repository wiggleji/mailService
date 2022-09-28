package com.example.api.email;

import com.example.api.email.dto.EmailQueueDirectDto;
import com.example.api.email.dto.EmailQueueScheduleDto;
import com.example.api.email.dto.EmailRequestDto;
import com.example.api.user.UserService;
import com.example.core.entity.email.Email;
import com.example.core.entity.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@Transactional(readOnly = true)
public class EmailQueueService {

    private final UserService userService;

    private final EmailService emailService;

    private final EmailMetadataService emailMetadataService;

    private final KafkaEmailProducer kafkaEmailProducer;

    public EmailQueueService(UserService userService, EmailService emailService, EmailMetadataService emailMetadataService, KafkaEmailProducer kafkaEmailProducer) {
        this.userService = userService;
        this.emailService = emailService;
        this.emailMetadataService = emailMetadataService;
        this.kafkaEmailProducer = kafkaEmailProducer;
    }

    @Transactional(readOnly = false)
    public EmailRequestDto queueEmail(EmailRequestDto requestDto) {

        // validate EmailMetadata with request User
        emailMetadataService.validMailMetadata(requestDto);

        if (requestDto.getDateTimeSend().isAfter(LocalDateTime.now())) {
            // 예약전송: 전송시간 > 현재시간
            return queueScheduleEmail(requestDto);
        } else {
            // 즉시전송: 전송시간 <= 현재시간
            return queueDirectEmail(requestDto);
        }
    }

    private EmailRequestDto queueScheduleEmail(EmailRequestDto requestDto) {
        // 예약 전송
        // EmailFolder=SCHEDULED 로 email Entity 저장
        // {UUID:(emailId+dateTimeSend).toString} : {emailId, dateTimeSend} -> queue
        Email emailScheduled = emailService.createScheduledEmail(requestDto);
        EmailQueueScheduleDto scheduleDto = EmailQueueScheduleDto.fromEmailEntity(emailScheduled);
        // Kafka producer 등록
        kafkaEmailProducer.sendMessage("email-schedule", scheduleDto.getStringUuid(), scheduleDto);

        // 생성 내역 반환
        return requestDto;
    }

    private EmailRequestDto queueDirectEmail(EmailRequestDto requestDto) {
        // Kafka producer 등록
        // {UUID:(userId+dateTimeSend).toString}: {userId 포함한 메일 데이터} -> queue
        User requestUser = userService.loadUserFromSecurityContextHolder();
        EmailQueueDirectDto directDto = requestDto.toEmailQueueDirectDto(requestUser.getId());
        kafkaEmailProducer.sendMessage("email-direct", directDto.getStringUuid(), directDto);

        // 생성 내역 반환
        return requestDto;
    }
}
