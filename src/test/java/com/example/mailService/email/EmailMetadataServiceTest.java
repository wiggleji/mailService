package com.example.mailService.email;

import com.example.mailService.base.BaseTestSetup;
import com.example.mailService.email.dto.UserEmailInfoCreateDto;
import com.example.mailService.user.entity.User;
import com.example.mailService.email.entity.EmailMetadata;
import com.example.mailService.exception.ResourceAlreadyExistException;
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
class EmailMetadataServiceTest extends BaseTestSetup {

    @Autowired
    private EmailMetadataService emailMetadataService;

    private UserEmailInfoCreateDto createTestDto(String email, User user) {
        return UserEmailInfoCreateDto.builder()
                .email(email)
                .username("testSMTP")
                .password("testPassword")
                .smtpHost("smtp.test.com")
                .smtpPort(465L)
                .user(user)
                .build();
    }

    @Test
    @WithMockUser(username = USERNAME, password = PASSWORD)
    public void UserEmailInfoService_createUserEmailInfo__SUCCESS() throws Exception {
        // given
        UserEmailInfoCreateDto createDto = createTestDto("test@testEmail.com", testUser);

        // when
        EmailMetadata emailMetadata = emailMetadataService.createUserEmailInfo(createDto);
        EmailMetadata retrieveEmailMetadata = emailMetadataService.loadUserEmailInfoById(emailMetadata.getId());

        // then
        Assertions.assertThat(emailMetadata.getEmail()).isEqualTo("test@testEmail.com");
        Assertions.assertThat(emailMetadata.getUser()).isEqualTo(testUser);

        Assertions.assertThat(retrieveEmailMetadata.getUser()).isEqualTo(testUser);
        Assertions.assertThat(retrieveEmailMetadata.getId()).isEqualTo(emailMetadata.getId());
    }

    @Test
    @WithMockUser(username = USERNAME, password = PASSWORD)
    public void UserEmailInfoService_loadUserEmailInfo__SUCCESS() throws Exception {
        // given
        UserEmailInfoCreateDto createDto1 = createTestDto("test1@testEmail.com", testUser);
        UserEmailInfoCreateDto createDto2 = createTestDto("test2@testEmail.com", testUser);

        // when
        EmailMetadata emailMetadata1 = emailMetadataService.createUserEmailInfo(createDto1);
        EmailMetadata emailMetadata2 = emailMetadataService.createUserEmailInfo(createDto2);

        // then
        List<EmailMetadata> emailInfoList = emailMetadataService.loadUserEmailInfoListByUserId(testUser.getId());

        Assertions.assertThat(emailInfoList).isEqualTo(Arrays.asList(emailMetadata1, emailMetadata2));
    }

    @Test
    @WithMockUser(username = USERNAME, password = PASSWORD)
    public void UserEmailInfoService_createUserEmailInfo__WRONG_USER() throws Exception {
        // given
        UserEmailInfoCreateDto wrongCreateDto = createTestDto("test1@testEmail.com",
                User.builder()
                        .username("anotherUser").email("wrongUser@test.com").password("wrongPassword")
                        .build());

        // when

        // then
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> emailMetadataService.createUserEmailInfo(wrongCreateDto));
    }

    @Test
    @WithMockUser(username = USERNAME, password = PASSWORD)
    public void UserEmailInfoService_createUserEmailInfo__DUPLICATE() throws Exception {
        // given
        UserEmailInfoCreateDto duplicateCreateDto = createTestDto("test@testEmail.com", testUser);
        // when
        EmailMetadata emailMetadata = emailMetadataService.createUserEmailInfo(duplicateCreateDto);

        // then
        org.junit.jupiter.api.Assertions.assertThrows(ResourceAlreadyExistException.class, () -> emailMetadataService.createUserEmailInfo(duplicateCreateDto));
    }
}