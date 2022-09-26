package com.example.api.email.controller;

import com.example.api.email.EmailSendService;
import com.example.api.email.EmailTestSetup;
import com.example.api.email.MailSender;
import com.example.api.email.dto.EmailCreateDto;
import com.example.api.email.dto.EmailDto;
import com.example.core.repository.EmailMetadataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.Message;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

@Transactional
@ExtendWith(MockitoExtension.class)
@SpringBootTest
@DisplayName("EmailSendController")
class EmailSendControllerTest extends EmailTestSetup {

    private final EmailController emailController;

    private final EmailSendController emailSendController;

    private final EmailMetadataRepository metadataRepository;

    @SpyBean
    private MailSender mailSender;

    @InjectMocks
    private EmailSendService emailSendService;

    @Autowired
    public EmailSendControllerTest(EmailController emailController, EmailSendController emailSendController, EmailMetadataRepository metadataRepository, MailSender mailSender, EmailSendService emailSendService) {
        this.emailController = emailController;
        this.emailSendController = emailSendController;
        this.metadataRepository = metadataRepository;
        this.mailSender = mailSender;
        this.emailSendService = emailSendService;
    }

    @BeforeEach
    public void beforeEach() {
        super.beforeEach();
        metadataRepository.save(
                testEmailMetadata(testUser.getEmail(), testUser));
    }

    @Test
    @WithMockUser(username = USERNAME, password = PASSWORD)
    @DisplayName("Java Mail API mocking 처리된 EmailController 메일전송 테스트")
    public void EmailController__sendEmail__SUCCESS() throws Exception {
        // given
        EmailCreateDto createDto = testEmailCreateDto(
                testUser.getEmail(),
                "toOtherMail@test.com",
                "testEmail",
                "cc1@test1.com, cc2@test2.com",
                "bcc1@test1.com, bcc2@test2.com"
        );
        doNothing().when(mailSender).sendMessage(any(Message.class));

        // when
        ResponseEntity<EmailDto> response = emailSendController.sendEmail(createDto);

        // then
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        ResponseEntity<EmailDto> emailDetail = emailController.emailDetail(response.getBody().getId());
        assertThat(emailDetail.getBody()).isNotNull();
        assertThat(emailDetail.getBody().getSubject()).isEqualTo(createDto.getSubject());
    }
}