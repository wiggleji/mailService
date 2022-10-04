package com.example.api.email;

import com.example.core.exception.ResourceNotFoundException;
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
class EmailServiceTest extends EmailTestSetup {

    private final EmailService emailService;

    @Autowired
    public EmailServiceTest(EmailService emailService) {
        this.emailService = emailService;
    }

    @Test
    @WithMockUser(username = USERNAME, password = PASSWORD)
    public void EmailService_loadEmailById__wrongId__empty__FAIL() throws Exception {
        // given
        Long wrongEmailId = 999999L;
        // when

        // then
        org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class, () -> emailService.loadEmailByIdAndUserId(wrongEmailId));
    }
}
