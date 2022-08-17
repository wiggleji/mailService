package com.example.mailService.service;

import com.example.mailService.base.CustomTestSetup;
import com.example.mailService.domain.dto.EmailCreateDto;
import com.example.mailService.domain.entity.Email;
import com.example.mailService.exception.ResourceNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class EmailServiceTest extends CustomTestSetup {

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
        Long emailId1 = emailService.createEmail(testEmailDto1);
        Long emailId2 = emailService.createEmail(testEmailDto2);

        // when
        List<Email> emailList = emailService.loadEmailListByUserId(testUser.getId());

        List<Long> emailIdList = emailList.stream().map(Email::getId).collect(Collectors.toList());

        // then
        Assertions.assertThat(emailList.size()).isEqualTo(2);
        Assertions.assertThat(emailIdList).isEqualTo(Arrays.asList(emailId1, emailId2));
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
        Long emailId = emailService.createEmail(testEmailDto1);

        // when
        Email email = emailService.loadEmailById(emailId);

        // then
        Assertions.assertThat(email.getId()).isEqualTo(emailId);
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