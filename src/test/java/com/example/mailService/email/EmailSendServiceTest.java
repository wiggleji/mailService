package com.example.mailService.email;

import com.example.mailService.base.BaseTestSetup;
import com.example.mailService.email.dto.EmailCreateDto;
import com.example.mailService.email.entity.Email;
import com.example.mailService.email.entity.EmailMetadata;
import com.example.mailService.exception.ResourceNotFoundException;
import com.example.mailService.repository.EmailMetadataRepository;
import com.example.mailService.utils.MailSender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.Message;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@Transactional
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class EmailSendServiceTest extends BaseTestSetup implements EmailTestBuilder {

    @InjectMocks
    private EmailSendService emailSendService;

    @SpyBean
    private MailSender mailSender;

    private final EmailService emailService;

    private final EmailMetadataRepository metadataRepository;

    EmailMetadata testMetadata;


    @Autowired
    public EmailSendServiceTest(EmailSendService emailSendService, MailSender mailSender, EmailService emailService, EmailMetadataRepository metadataRepository) {
        this.emailSendService = emailSendService;
        this.mailSender = mailSender;
        this.emailService = emailService;
        this.metadataRepository = metadataRepository;
    }

    // 시나리오
    // 사용자: testUser from BaseTestSetup
    // 데이터: 메일 정보 (beforeEach) & 메시지 (메소드로 제공)
    // 1. 메시지 생성 -> mock 적용


    @BeforeEach
    protected void beforeEach() {
        super.beforeEach();
        testMetadata = metadataRepository.save(
                EmailMetadata.builder()
                        .email("testUser@testMail.com")
                        .username("testUser")
                        .password("testPassword")
                        .smtpHost("smtp.testMail.com")
                        .smtpPort(465L)
                        .user(testUser)
                        .build()
        );
    }

    private EmailCreateDto testEmailCreateDto(EmailMetadata metadata, Long userId) {
        return testEmailCreateDto(
                metadata.getEmail(), "to@otherMail.com",
                "to other mail service",
                "cc1@otherMail.com, cc2@otherMail.com", "bcc1@otherMail.com, bcc2@otherMail.com",
                userId);
    }

    @Test
    @WithMockUser(username = USERNAME, password = PASSWORD)
    public void EmailSendService__sendEmail__SUCCESS() throws Exception {
        // given
        // MailSender.sendMessage 는 mock 처리 (doNothing)
        // MailSender.sendMailByEmailCreateDto 를 포함한 그 외는 정상처리 (SpyBean)
        doNothing().when(mailSender).sendMessage(any(Message.class));

        // when
        Email email = emailSendService.sendEmail(testEmailCreateDto(testMetadata, testUser.getId()));

        // then
        Email loadEmailById = emailService.loadEmailById(email.getId());

        assertThat(email).isEqualTo(loadEmailById);
    }

    @Test
    @WithMockUser(username = USERNAME, password = PASSWORD)
    public void EmailSendService__sendEmail__FAIL() throws Exception {
        // given
        createCompareUser();
        EmailMetadata compareMetadata = EmailMetadata.builder()
                .email(compareUser.getEmail())
                .username("compareUser")
                .password("comparePassword")
                .smtpHost("smtp.compare.com")
                .smtpPort(456L)
                .user(compareUser)
                .build();
        metadataRepository.save(compareMetadata);
        doNothing().when(mailSender).sendMessage(any(Message.class));


        // when

        // then
        Assertions.assertThrows(IllegalArgumentException.class, () -> emailSendService.sendEmail(testEmailCreateDto(compareMetadata, compareUser.getId())));
        Assertions.assertThrows(ResourceNotFoundException.class, () -> emailSendService.sendEmail(testEmailCreateDto(testMetadata, 9999L)));
    }

}