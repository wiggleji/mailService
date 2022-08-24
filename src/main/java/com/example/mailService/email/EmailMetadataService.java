package com.example.mailService.email;

import com.example.mailService.email.dto.EmailCreateDto;
import com.example.mailService.email.dto.MailMetadataCreateDto;
import com.example.mailService.user.entity.User;
import com.example.mailService.email.entity.EmailMetadata;
import com.example.mailService.exception.ResourceAlreadyExistException;
import com.example.mailService.exception.ResourceNotFoundException;
import com.example.mailService.repository.EmailMetadataRepository;
import com.example.mailService.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EmailMetadataService {

    private final EmailMetadataRepository emailMetadataRepository;

    private final UserService userService;

    // 유저 메일정보 조회 / 생성

    public List<EmailMetadata> loadEmailMetadataListByUserId(Long userId) {
        return emailMetadataRepository.findAllByUser_Id(userId);
    }

    public EmailMetadata loadEmailMetadataById(Long metadataId) {
        return emailMetadataRepository.findById(metadataId)
                .orElseThrow(() -> new ResourceNotFoundException("EmailMetadata not found by id: " + metadataId));
    }

    public EmailMetadata loadEmailMetadataByEmailAndUserId(String email, Long userId) {
        return emailMetadataRepository.findByEmailAndUser_Id(email, userId)
                .orElseThrow(() -> new ResourceNotFoundException("EmailMetadata not found by email & userId: " + email + userId));
    }

    @Transactional(readOnly = false)
    public EmailMetadata createEmailMetadata(MailMetadataCreateDto createDto) {
        // TODO: Java mail API 를 사용해서 유효한 메일 메타데이터 인지 검증해야함
        User requestUser = userService.loadUserFromSecurityContextHolder();
        Optional<EmailMetadata> existingUserEmailInfo = emailMetadataRepository.findByEmailAndUser_Id(createDto.getEmail(), requestUser.getId());
        if (!existingUserEmailInfo.isPresent() & createDto.getUser().equals(requestUser)) {
            return emailMetadataRepository.save(createDto.toEntity());
        } else if (existingUserEmailInfo.isPresent()) {
            throw new ResourceAlreadyExistException("Resource already exist with: " + existingUserEmailInfo);
        } else throw new IllegalArgumentException("Request user is not equal. User: " + requestUser.getId() + ", Request:" + createDto.getUser().getId());
    }

    public boolean validMailMetadata(EmailCreateDto createDto) {
        // 요청자 정보와 메일 정보 검증
        User user = userService.loadUserFromSecurityContextHolder();
        EmailMetadata emailMetadata = loadEmailMetadataByEmailAndUserId(createDto.getEmailFrom(), createDto.getUserId());
        return emailMetadata.getUser().equals(user);
    }

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
