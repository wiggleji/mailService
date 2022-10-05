package com.example.mailService.email.service;

import com.example.core.repository.EmailRepository;
import com.example.core.service.CoreEmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailService extends CoreEmailService {

    private final EmailRepository emailRepository;

}
