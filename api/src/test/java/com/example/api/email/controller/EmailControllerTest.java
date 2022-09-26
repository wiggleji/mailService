package com.example.api.email.controller;

import com.example.api.email.EmailSendService;
import com.example.api.email.EmailTestSetup;
import com.example.api.email.MailSender;
import com.example.api.email.dto.EmailDto;
import com.example.core.entity.email.Email;
import com.example.core.exception.ResourceNotFoundException;
import com.example.core.repository.EmailMetadataRepository;
import com.example.core.repository.EmailRepository;
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


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@ExtendWith(MockitoExtension.class)
@SpringBootTest
@DisplayName("EmailController")
class EmailControllerTest extends EmailTestSetup {

    private final EmailController emailController;

    private final EmailRepository emailRepository;

    private final EmailMetadataRepository metadataRepository;

    @SpyBean
    private MailSender mailSender;

    @InjectMocks
    private EmailSendService emailSendService;

    @Autowired
    public EmailControllerTest(EmailController emailController, EmailRepository emailRepository, EmailMetadataRepository metadataRepository, MailSender mailSender, EmailSendService emailSendService) {
        this.emailController = emailController;
        this.emailRepository = emailRepository;
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
    public void EmailController__emailList() throws Exception {
        // given
        emailRepository.save(testEmail("testMail1", testUser));
        emailRepository.save(testEmail("testMail2", testUser));
        emailRepository.save(testEmail("testMail3", testUser));

        // when
        ResponseEntity<List<EmailDto>> response = emailController.emailList();

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<EmailDto> body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.size()).isEqualTo(3);
    }

    @Test
    @WithMockUser(username = USERNAME, password = PASSWORD)
    public void EmailController__emailList__empty() throws Exception {
        // given
        createCompareUser();
        emailRepository.save(testEmail("testMail1", compareUser));
        emailRepository.save(testEmail("testMail2", compareUser));
        emailRepository.save(testEmail("testMail3", compareUser));

        // when
        ResponseEntity<List<EmailDto>> response = emailController.emailList();

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        List<EmailDto> body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.size()).isEqualTo(0);
    }

    @Test
    @WithMockUser(username = USERNAME, password = PASSWORD)
    public void EmailController__emailDetail() throws Exception {
        // given
        Email testMail1 = emailRepository.save(testEmail("testMail1", testUser));

        // when
        ResponseEntity<EmailDto> response = emailController.emailDetail(testMail1.getId());

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        EmailDto body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.getId()).isEqualTo(testMail1.getId());
    }

    @Test
    @WithMockUser(username = USERNAME, password = PASSWORD)
    public void EmailController__emailDetail__NOT_FOUND() throws Exception {
        // given
        createCompareUser();
        Email testMail1 = emailRepository.save(testEmail("testMail1", compareUser));

        // when

        // then
        org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class, () -> emailController.emailDetail(testMail1.getId()));
    }
}