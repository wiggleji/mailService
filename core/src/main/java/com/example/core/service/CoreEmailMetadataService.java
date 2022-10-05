package com.example.core.service;

import com.example.core.entity.email.EmailMetadata;
import com.example.core.exception.ResourceNotFoundException;
import com.example.core.repository.EmailMetadataRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class CoreEmailMetadataService {

    @Autowired
    private EmailMetadataRepository emailMetadataRepository;

    public EmailMetadata loadEmailMetadataByEmailAndUserId(String email, Long userId) {
        return emailMetadataRepository.findByEmailAndUser_Id(email, userId)
                .orElseThrow(() -> new ResourceNotFoundException("EmailMetadata not found by email & userId: " + email + userId));
    }
}
