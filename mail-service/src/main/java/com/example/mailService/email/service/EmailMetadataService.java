package com.example.mailService.email.service;

import com.example.core.entity.email.EmailMetadata;
import com.example.core.repository.EmailMetadataRepository;
import com.example.core.repository.EmailRepository;
import com.example.core.service.CoreEmailMetadataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailMetadataService extends CoreEmailMetadataService {

    private final EmailMetadataRepository emailMetadataRepository;

    public Properties generateEmailMetadataProperty(EmailMetadata metadata) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", metadata.getSmtpHost());
        properties.put("mail.smtp.port", metadata.getSmtpPort());
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.trust", metadata.getSmtpHost());
        return properties;
    }
}
