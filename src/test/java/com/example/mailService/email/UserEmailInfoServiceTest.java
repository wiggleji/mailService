package com.example.mailService.email;

import com.example.mailService.base.BaseTestSetup;
import com.example.mailService.email.dto.UserEmailInfoCreateDto;
import com.example.mailService.user.entity.User;
import com.example.mailService.email.entity.UserEmailInfo;
import com.example.mailService.email.UserEmailInfoService;
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
class UserEmailInfoServiceTest extends BaseTestSetup {

    @Autowired
    private UserEmailInfoService userEmailInfoService;

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
        UserEmailInfo userEmailInfo = userEmailInfoService.createUserEmailInfo(createDto);
        UserEmailInfo retrieveUserEmailInfo = userEmailInfoService.loadUserEmailInfoById(userEmailInfo.getId());

        // then
        Assertions.assertThat(userEmailInfo.getEmail()).isEqualTo("test@testEmail.com");
        Assertions.assertThat(userEmailInfo.getUser()).isEqualTo(testUser);

        Assertions.assertThat(retrieveUserEmailInfo.getUser()).isEqualTo(testUser);
        Assertions.assertThat(retrieveUserEmailInfo.getId()).isEqualTo(userEmailInfo.getId());
    }

    @Test
    @WithMockUser(username = USERNAME, password = PASSWORD)
    public void UserEmailInfoService_loadUserEmailInfo__SUCCESS() throws Exception {
        // given
        UserEmailInfoCreateDto createDto1 = createTestDto("test1@testEmail.com", testUser);
        UserEmailInfoCreateDto createDto2 = createTestDto("test2@testEmail.com", testUser);

        // when
        UserEmailInfo userEmailInfo1 = userEmailInfoService.createUserEmailInfo(createDto1);
        UserEmailInfo userEmailInfo2 = userEmailInfoService.createUserEmailInfo(createDto2);

        // then
        List<UserEmailInfo> emailInfoList = userEmailInfoService.loadUserEmailInfoListByUserId(testUser.getId());

        Assertions.assertThat(emailInfoList).isEqualTo(Arrays.asList(userEmailInfo1, userEmailInfo2));
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
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> userEmailInfoService.createUserEmailInfo(wrongCreateDto));
    }

    @Test
    @WithMockUser(username = USERNAME, password = PASSWORD)
    public void UserEmailInfoService_createUserEmailInfo__DUPLICATE() throws Exception {
        // given
        UserEmailInfoCreateDto duplicateCreateDto = createTestDto("test@testEmail.com", testUser);
        // when
        UserEmailInfo userEmailInfo = userEmailInfoService.createUserEmailInfo(duplicateCreateDto);

        // then
        org.junit.jupiter.api.Assertions.assertThrows(ResourceAlreadyExistException.class, () -> userEmailInfoService.createUserEmailInfo(duplicateCreateDto));
    }
}