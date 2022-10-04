package com.example.core.service;

import com.example.core.entity.email.Email;
import com.example.core.exception.ResourceNotFoundException;
import com.example.core.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class EmailService {

    @Autowired
    protected EmailRepository emailRepository;

    protected Email loadEmailById(Long emailId) {
        return emailRepository.findEmailById(emailId)
                .orElseThrow(() -> new ResourceNotFoundException("Email not found by emailId: " + emailId));
    }

    protected Email createEmail(Email email) {
        return emailRepository.save(email);
    }
}
