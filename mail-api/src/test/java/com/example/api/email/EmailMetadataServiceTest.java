package com.example.api.email;

import com.example.api.email.dto.EmailMetadataCreateDto;
import com.example.core.entity.email.EmailMetadata;
import com.example.core.exception.ResourceAlreadyExistException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class EmailMetadataServiceTest extends EmailTestSetup {

//    @Autowired
    private final EmailMetadataService emailMetadataService;

    @Autowired
    public EmailMetadataServiceTest(EmailMetadataService emailMetadataService) {
        this.emailMetadataService = emailMetadataService;
    }

    @Test
    @WithMockUser(username = USERNAME, password = PASSWORD)
    public void UserEmailInfoService_createUserEmailInfo__SUCCESS() throws Exception {
        // given
        EmailMetadataCreateDto createDto = testEmailMetadataCreateDto("test@testEmail.com");

        // when
        EmailMetadata emailMetadata = emailMetadataService.createEmailMetadata(createDto);
        EmailMetadata retrieveEmailMetadata = emailMetadataService.loadEmailMetadataById(emailMetadata.getId());

        // then
        assertThat(emailMetadata.getEmail()).isEqualTo("test@testEmail.com");
        assertThat(emailMetadata.getUser()).isEqualTo(testUser);

        assertThat(retrieveEmailMetadata.getUser()).isEqualTo(testUser);
        assertThat(retrieveEmailMetadata.getId()).isEqualTo(emailMetadata.getId());
    }

    @Test
    @WithMockUser(username = USERNAME, password = PASSWORD)
    public void UserEmailInfoService_createUserEmailInfo__ResourceAlreadyExistException__FAIL() throws Exception {
        // given
        EmailMetadataCreateDto duplicateCreateDto = testEmailMetadataCreateDto("test@testEmail.com");
        // when
        EmailMetadata emailMetadata = emailMetadataService.createEmailMetadata(duplicateCreateDto);

        // then
        org.junit.jupiter.api.Assertions.assertThrows(ResourceAlreadyExistException.class, () -> emailMetadataService.createEmailMetadata(duplicateCreateDto));
    }

    @Test
    @WithMockUser(username = USERNAME, password = PASSWORD)
    public void UserEmailInfoService_loadUserEmailInfo__SUCCESS() throws Exception {
        // given
        EmailMetadataCreateDto createDto1 = testEmailMetadataCreateDto("test1@testEmail.com");
        EmailMetadataCreateDto createDto2 = testEmailMetadataCreateDto("test2@testEmail.com");

        // when
        EmailMetadata emailMetadata1 = emailMetadataService.createEmailMetadata(createDto1);
        EmailMetadata emailMetadata2 = emailMetadataService.createEmailMetadata(createDto2);

        // then
        List<EmailMetadata> emailInfoList = emailMetadataService.loadEmailMetadataListByUserId();

        assertThat(emailInfoList).isEqualTo(Arrays.asList(emailMetadata1, emailMetadata2));
    }

}