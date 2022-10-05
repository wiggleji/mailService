package com.example.api.email;

import com.example.core.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class EmailWithUserContextServiceTest extends EmailTestSetup {

    private final EmailWithUserContextService emailWithUserContextService;

    @Autowired
    public EmailWithUserContextServiceTest(EmailWithUserContextService emailWithUserContextService) {
        this.emailWithUserContextService = emailWithUserContextService;
    }

    @Test
    @WithMockUser(username = USERNAME, password = PASSWORD)
    public void EmailService_loadEmailById__wrongId__empty__FAIL() throws Exception {
        // given
        Long wrongEmailId = 999999L;
        // when

        // then
        org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class, () -> emailWithUserContextService.loadEmailByIdAndUserId(wrongEmailId));
    }
}
