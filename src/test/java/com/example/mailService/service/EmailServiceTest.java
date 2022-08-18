package com.example.mailService.service;

import com.example.mailService.base.BaseTestSetup;
import com.example.mailService.domain.dto.EmailCreateDto;
import com.example.mailService.domain.entity.Email;
import com.example.mailService.exception.ResourceNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Transactional
class EmailServiceTest extends BaseTestSetup {

    @Autowired
    private EmailService emailService;

    @Test
    @WithMockUser(username = USERNAME, password = PASSWORD)
    public void EmailService_loadEmailListByUserId() throws Exception {
        // given
        EmailCreateDto testEmailDto1 = EmailCreateDto.builder()
                .userId(testUser.getId())
                .emailFrom("from@test.com")
                .emailTo("to@test.com")
                .subject("testMailSubject1")
                .build();
        EmailCreateDto testEmailDto2 = EmailCreateDto.builder()
                .userId(testUser.getId())
                .emailFrom("from@test.com")
                .emailTo("to@test.com")
                .subject("testMailSubject2")
                .build();
        Email email1 = emailService.createEmail(testEmailDto1);
        Email email2 = emailService.createEmail(testEmailDto2);

        // when
        List<Email> emailList = emailService.loadEmailListByUserId(testUser.getId());

        // then
        Assertions.assertThat(emailList.size()).isEqualTo(2);
        Assertions.assertThat(emailList).isEqualTo(Arrays.asList(email1, email2));
    }

    @Test
    @WithMockUser(username = USERNAME, password = PASSWORD)
    public void EmailService_createEmail_loadEmailById() throws Exception {
        // given
        EmailCreateDto testEmailDto1 = EmailCreateDto.builder()
                .userId(testUser.getId())
                .emailFrom("from@test.com")
                .emailTo("to@test.com")
                .subject("testMailSubject1")
                .build();
        Email newEmail = emailService.createEmail(testEmailDto1);

        // when
        Email email = emailService.loadEmailById(newEmail.getId());

        // then
        Assertions.assertThat(email.getId()).isEqualTo(newEmail.getId());
    }

    @Test
    public void EmailService_loadEmailById__wrongId__FAIL() throws Exception {
        // given
        Long wrongEmailId = 999999L;

        // when

        // then
        org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class, () -> emailService.loadEmailById(wrongEmailId));
    }
}
