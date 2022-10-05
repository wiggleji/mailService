package com.example.api.email.controller;

import com.example.api.email.EmailTestSetup;
import com.example.api.email.dto.EmailMetadataCreateDto;
import com.example.core.dto.EmailMetadataDto;
import com.example.api.email.dto.EmailMetadataUpdateDto;
import com.example.core.entity.email.EmailMetadata;
import com.example.core.exception.ResourceAlreadyExistException;
import com.example.core.exception.ResourceNotFoundException;
import com.example.core.repository.EmailMetadataRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
@DisplayName("EmailMetadataController")
class EmailMetadataControllerTest extends EmailTestSetup {

    private final EmailMetadataController metadataController;

    private final EmailMetadataRepository metadataRepository;

    private EmailMetadata testUserMetadata;

    @Autowired
    public EmailMetadataControllerTest(EmailMetadataController metadataController, EmailMetadataRepository metadataRepository) {
        this.metadataController = metadataController;
        this.metadataRepository = metadataRepository;
    }

    @BeforeEach
    public void beforeEach() {
        super.beforeEach();
        testUserMetadata = metadataRepository.save(
                testEmailMetadata(testUser.getEmail(), testUser));
    }

    @Test
    @WithMockUser(username = USERNAME, password = PASSWORD)
    public void EmailMetadataController__emailMetadataList__SUCCESS() throws Exception {
        // given
        metadataRepository.save(testEmailMetadata("testMail1@mail1.com", testUser));
        metadataRepository.save(testEmailMetadata("testMail2@mail2.com", testUser));
        metadataRepository.save(testEmailMetadata("testMail3@mail3.com", testUser));

        // when
        ResponseEntity<List<EmailMetadataDto>> response = metadataController.emailMetadataList();

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<EmailMetadataDto> body = response.getBody();
        assertThat(body).isNotNull();
        // metadata from beforeEach + method = 4
        assertThat(body.size()).isEqualTo(4);
    }

    @Test
    @WithMockUser(username = USERNAME, password = PASSWORD)
    public void EmailMetadataController__emailMetadataList__NO_COTENT() throws Exception {
        // given
        metadataRepository.deleteAll();

        // when
        ResponseEntity<List<EmailMetadataDto>> response = metadataController.emailMetadataList();

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        List<EmailMetadataDto> body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.size()).isEqualTo(0);
    }

    @Test
    @WithMockUser(username = USERNAME, password = PASSWORD)
    public void EmailMetadataController__emailMetadataCreate__SUCCESS() throws Exception {
        // given
        EmailMetadataCreateDto createDto = testEmailMetadataCreateDto("testEmail@test.com");

        // when
        ResponseEntity<EmailMetadataDto> response = metadataController.emailMetadataCreate(createDto);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        EmailMetadataDto body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.getEmail()).isEqualTo(createDto.getEmail());
    }

    @Test
    @WithMockUser(username = USERNAME, password = PASSWORD)
    public void EmailMetadataController__emailMetadataCreate__ResourceAlreadyExistException__FAIL() throws Exception {
        // given
        EmailMetadataCreateDto duplicateCreateDto = testEmailMetadataCreateDto(testUser.getEmail());

        // when

        // then
        Assertions.assertThrows(ResourceAlreadyExistException.class, () -> metadataController.emailMetadataCreate(duplicateCreateDto));
    }

    @Test
    @WithMockUser(username = USERNAME, password = PASSWORD)
    public void EmailMetadataController__emailMetadataDetail__SUCCESS() throws Exception {
        // given

        // when
        ResponseEntity<EmailMetadataDto> detailResponse = metadataController.emailMetadataDetail(testUserMetadata.getId());

        // then
        assertThat(detailResponse.getBody()).isNotNull();
        assertThat(detailResponse.getBody().getEmail()).isEqualTo(testUser.getEmail());
    }

    @Test
    @WithMockUser(username = USERNAME, password = PASSWORD)
    public void EmailMetadataController__emailMetadataDetail__NOT_FOUND() throws Exception {
        // given
        createCompareUser();
        EmailMetadata createDto = testEmailMetadata("testEmail@test.com", compareUser);
        EmailMetadata metadata = metadataRepository.save(createDto);

        // when

        // then
        org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class, () -> metadataController.emailMetadataDetail(metadata.getId()));

    }


    @Test
    @WithMockUser(username = USERNAME, password = PASSWORD)
    public void EmailMetadataController__emailMetadataEdit__SUCCESS() throws Exception {
        // given
        EmailMetadataUpdateDto updateDto = testEmailMetadataUpdateDto(
                testUserMetadata,
                "updateEmail@test.com",
                "updateUsername",
                testUser);

        // when
        ResponseEntity<EmailMetadataDto> editResponse = metadataController.emailMetadataUpdate(testUserMetadata.getId(), updateDto);
        ResponseEntity<EmailMetadataDto> detailResponse = metadataController.emailMetadataDetail(testUserMetadata.getId());

        // then
        assertThat(editResponse.getBody()).isNotNull();
        assertThat(editResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(detailResponse.getBody()).isNotNull();
        assertThat(detailResponse.getBody().getEmail()).isEqualTo(updateDto.getEmail());
        assertThat(detailResponse.getBody().getUsername()).isEqualTo(updateDto.getUsername());
    }

    @Test
    @WithMockUser(username = USERNAME, password = PASSWORD)
    public void EmailMetadataController__emailMetadataEdit__FAIL() throws Exception {
        // given
        // TODO: emailMetadataCreate 의 exceptionHandler (ControllerAdvice) 적용 후 실패 케이스 작성

        // when

        // then
    }

}