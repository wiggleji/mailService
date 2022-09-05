package com.example.mailService.email.controller;

import com.example.mailService.email.EmailMetadataService;
import com.example.mailService.email.dto.EmailMetadataCreateDto;
import com.example.mailService.email.dto.EmailMetadataDto;
import com.example.mailService.email.dto.EmailMetadataUpdateDto;
import com.example.mailService.email.entity.EmailMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
        Optional<EmailMetadata> metadata = metadataService.loadEmailMetadataById(metadataId);
        return metadata.map(value -> new ResponseEntity<>(EmailMetadataDto.from(value), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    // put
    @PutMapping("/{metadataId}")
    public ResponseEntity<EmailMetadataDto> emailMetadataUpdate(@PathVariable Long metadataId, @Valid @RequestBody EmailMetadataUpdateDto updateDto) {
        Optional<EmailMetadata> metadata = metadataService.loadEmailMetadataById(metadataId);
        if (metadata.isPresent()) {
            EmailMetadata updateEmailMetadata = metadataService.updateEmailMetadata(metadata.get().getId(), updateDto);
            return new ResponseEntity<>(EmailMetadataDto.from(updateEmailMetadata), HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
