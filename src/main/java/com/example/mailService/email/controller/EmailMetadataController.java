package com.example.mailService.email.controller;

import com.example.mailService.email.EmailMetadataService;
import com.example.mailService.email.dto.EmailMetadataDto;
import com.example.mailService.email.entity.EmailMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email-metadata")
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
public class EmailMetadataController {

    private final EmailMetadataService metadataService;

    // get-list
    @GetMapping("/")
    public List<EmailMetadataDto> emailMetadataList() {
        List<EmailMetadata> metadataList = metadataService.loadEmailMetadataListByUserId();
        return EmailMetadataDto.from(metadataList);
    }

    // get

    @GetMapping("/{metadataId}")
    public EmailMetadataDto emailMetadataDetail(@PathVariable Long metadataId) {
        EmailMetadata metadata = metadataService.loadEmailMetadataById(metadataId);
        return EmailMetadataDto.from(metadata);
    }

    // put

    // delete
}
