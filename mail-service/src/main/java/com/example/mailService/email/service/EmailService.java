package com.example.mailService.email.service;

import com.example.core.entity.email.Email;
import com.example.core.entity.email.EmailFolder;
import com.example.core.repository.EmailRepository;
import com.example.core.service.CoreEmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class EmailService extends CoreEmailService {

    private final EmailRepository emailRepository;

    public List<Email> findScheduledEmailByDatetimeSend(LocalDateTime datetime) {
        return emailRepository.findEmailsByEmailFolderAndDateTimeSend(EmailFolder.SCHEDULED, datetime);
    }
}
