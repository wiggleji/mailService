package com.example.mailService.email.dto;

import com.example.mailService.email.entity.EmailMetadata;

import java.util.List;
import java.util.stream.Collectors;

public class MailMetadataListDto {
    List<MailMetadataDto> mailMetadataDtoList;

    public static List<MailMetadataDto> from(List<EmailMetadata> emailMetadata) {
        return emailMetadata.stream()
                .map(MailMetadataDto::from)
                .collect(Collectors.toList());
    }
}
