package com.example.mailService.email;

import com.example.mailService.email.dto.EmailCreateDto;
import com.example.mailService.email.dto.EmailMetadataCreateDto;
import com.example.mailService.email.dto.EmailMetadataUpdateDto;
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
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Properties;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EmailMetadataService {

    private final EmailMetadataRepository emailMetadataRepository;

    private final UserService userService;

    // 유저 메일정보 조회 / 생성

    public List<EmailMetadata> loadEmailMetadataListByUserId() {
        User requestUser = userService.loadUserFromSecurityContextHolder();
        return emailMetadataRepository.findAllByUser_Id(requestUser.getId());
    }

    public Optional<EmailMetadata> loadEmailMetadataById(Long metadataId) {
        User requestUser = userService.loadUserFromSecurityContextHolder();
        return emailMetadataRepository.findByIdAndUser_Id(metadataId, requestUser.getId());
    }

    public EmailMetadata loadEmailMetadataByEmailAndUserId(String email, Long userId) {
        return emailMetadataRepository.findByEmailAndUser_Id(email, userId)
                .orElseThrow(() -> new ResourceNotFoundException("EmailMetadata not found by email & userId: " + email + userId));
    }

    @Transactional(readOnly = false)
    public EmailMetadata createEmailMetadata(EmailMetadataCreateDto createDto) {
        // TODO: Java mail API 를 사용해서 유효한 메일 메타데이터 인지 검증해야함
        User requestUser = userService.loadUserFromSecurityContextHolder();
        Optional<EmailMetadata> existingUserEmailInfo = emailMetadataRepository.findByEmailAndUser_Id(createDto.getEmail(), requestUser.getId());
        if (existingUserEmailInfo.isPresent()) {
            throw new ResourceAlreadyExistException("Resource already exist with: " + existingUserEmailInfo);
        } else return emailMetadataRepository.save(createDto.toEntity(requestUser));
    }

    @Transactional(readOnly = false)
    public EmailMetadata updateEmailMetadata(Long metadataId, EmailMetadataUpdateDto updateDto) {
        User requestUser = userService.loadUserFromSecurityContextHolder();
        EmailMetadata metadata = emailMetadataRepository.findByIdAndUser_Id(metadataId, requestUser.getId())
                .orElseThrow(NoSuchElementException::new);

        metadata.update(updateDto);
        return emailMetadataRepository.save(metadata);
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
