package com.example.mailService.email.controller;

import com.example.mailService.email.EmailTestSetup;
import com.example.mailService.email.dto.EmailDto;
import com.example.mailService.email.entity.Email;
import com.example.mailService.repository.EmailMetadataRepository;
import com.example.mailService.repository.EmailRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@SpringBootTest
@DisplayName("EmailController")
class EmailControllerTest extends EmailTestSetup {

    private final EmailController emailController;

    private final EmailRepository emailRepository;

    private final EmailMetadataRepository metadataRepository;

    @Autowired
    public EmailControllerTest(EmailController emailController, EmailRepository emailRepository, EmailMetadataRepository metadataRepository) {
        this.emailController = emailController;
        this.emailRepository = emailRepository;
        this.metadataRepository = metadataRepository;
    }

    @BeforeEach
    public void beforeEach() {
        super.beforeEach();
        metadataRepository.save(testEmailMetadata(testUser));
    }

    @Test
    @WithMockUser(username = USERNAME, password = PASSWORD)
    public void EmailController__emailList() throws Exception {
        // given
        emailRepository.save(testEmail("testMail1", testUser));
        emailRepository.save(testEmail("testMail2", testUser));
        emailRepository.save(testEmail("testMail3", testUser));

        // when
        List<EmailDto> emailList = emailController.emailList();

        // then
        Assertions.assertThat(emailList.size()).isEqualTo(3);
    }

    @Test
    @WithMockUser(username = USERNAME, password = PASSWORD)
    public void EmailController__emailDetail() throws Exception {
        // given
        Email testMail1 = emailRepository.save(testEmail("testMail1", testUser));

        // when
        EmailDto testMailDto = emailController.emailDetail(testMail1.getId());

        // then
        Assertions.assertThat(testMailDto.getId()).isEqualTo(testMail1.getId());
    }
}