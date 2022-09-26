package com.example.api.email.controller;

import com.example.api.email.EmailMetadataService;
import com.example.api.email.dto.EmailMetadataCreateDto;
import com.example.api.email.dto.EmailMetadataDto;
import com.example.api.email.dto.EmailMetadataUpdateDto;
import com.example.core.entity.email.EmailMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email-metadata")
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
public class EmailMetadataController {

    private final EmailMetadataService metadataService;

    // get-list
    @GetMapping("/")
    public ResponseEntity<List<EmailMetadataDto>> emailMetadataList() {
        List<EmailMetadata> metadataList = metadataService.loadEmailMetadataListByUserId();

        if (metadataList.size() > 0)
            return new ResponseEntity<>(EmailMetadataDto.from(metadataList), HttpStatus.OK);
        else return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NO_CONTENT);
    }

    // post
    @PostMapping("/")
    public ResponseEntity<EmailMetadataDto> emailMetadataCreate(@RequestBody EmailMetadataCreateDto createDto) {
        // TODO: 유효한 메일정보인지 확인하는 로직
        EmailMetadata metadata = metadataService.createEmailMetadata(createDto);
        return new ResponseEntity<>(EmailMetadataDto.from(metadata), HttpStatus.CREATED);
    }

    // get

    @GetMapping("/{metadataId}")
    public ResponseEntity<EmailMetadataDto> emailMetadataDetail(@PathVariable Long metadataId) {
        EmailMetadata metadata = metadataService.loadEmailMetadataById(metadataId);
        return new ResponseEntity<>(EmailMetadataDto.from(metadata), HttpStatus.OK);
    }


    // put
    @PutMapping("/{metadataId}")
    public ResponseEntity<EmailMetadataDto> emailMetadataUpdate(@PathVariable Long metadataId, @Valid @RequestBody EmailMetadataUpdateDto updateDto) {
        EmailMetadata metadata = metadataService.loadEmailMetadataById(metadataId);
        EmailMetadata updateEmailMetadata = metadataService.updateEmailMetadata(metadata.getId(), updateDto);
        return new ResponseEntity<>(EmailMetadataDto.from(updateEmailMetadata), HttpStatus.OK);
    }
}
