package com.example.core.service;

import com.example.core.entity.email.Email;
import com.example.core.entity.email.EmailFolder;
import com.example.core.exception.ResourceNotFoundException;
import com.example.core.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class CoreEmailService {

    @Autowired
    protected EmailRepository emailRepository;

    public Email loadEmailById(Long emailId) {
        return emailRepository.findEmailById(emailId)
                .orElseThrow(() -> new ResourceNotFoundException("Email not found by emailId: " + emailId));
    }

    @Transactional(readOnly = false)
    public Email createEmail(Email email) {
        return emailRepository.save(email);
    }

    @Transactional(readOnly = false)
    public Email updateEmailToInbox(Email email) {
        email.updateEmailFolder(EmailFolder.INBOX);
        return emailRepository.save(email);
    }
}
