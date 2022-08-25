package com.example.mailService.email.controller;

import com.example.mailService.email.EmailTestSetup;
import com.example.mailService.repository.EmailMetadataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@DisplayName("EmailMetadataController")
class EmailMetadataControllerTest extends EmailTestSetup {

    private final EmailMetadataController metadataController;

    private final EmailMetadataRepository metadataRepository;

    @Autowired
    public EmailMetadataControllerTest(EmailMetadataController metadataController, EmailMetadataRepository metadataRepository) {
        this.metadataController = metadataController;
        this.metadataRepository = metadataRepository;
    }
}