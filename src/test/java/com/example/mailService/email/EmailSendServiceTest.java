package com.example.mailService.email;

import com.example.mailService.base.BaseTestSetup;
import com.example.mailService.email.dto.EmailCreateDto;
import com.example.mailService.email.entity.Email;
import com.example.mailService.email.entity.EmailMetadata;
import com.example.mailService.repository.EmailMetadataRepository;
import com.example.mailService.utils.MailSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.Message;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Transactional
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class EmailSendServiceTest extends BaseTestSetup {

    @InjectMocks
    private EmailSendService emailSendService;

    @SpyBean
    private MailSender mailSender;

    private final EmailService emailService;

    private final EmailMetadataRepository metadataRepository;


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
        EmailMetadata metadata = metadataRepository.save(
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

    private EmailCreateDto testEmailCreateDto() {
        return EmailCreateDto.builder()
                .emailFrom("testUser@testMail.com")
                .emailTo("to@otherMail.com")
                .subject("This is Test mail")
                .text("test mail text")
                .userId(testUser.getId())
                .emailToList("to@otherMail.com")
                .emailCcList("cc1@otherMail.com, cc2@otherMail.com")
                .emailBccList("bcc1@otherMail.com, bcc2@otherMail.com")
                .build();
    }

    @Test
    @WithMockUser(username = USERNAME, password = PASSWORD)
    public void EmailSendService__sendEmail() throws Exception {
        // given
        // MailSender.sendMessage 는 mock 처리 (doNothing)
        // MailSender.sendMailByEmailCreateDto 를 포함한 그 외는 정상처리 (SpyBean)
        doNothing().when(mailSender).sendMessage(any(Message.class));
        doCallRealMethod().when(mailSender).sendMessage(any(Message.class));

        // when
        Email email = emailSendService.sendEmail(testEmailCreateDto());

        // then
        Email loadEmailById = emailService.loadEmailById(email.getId());

        assertThat(email).isEqualTo(loadEmailById);
    }

}