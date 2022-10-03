package com.example.api.email;

import com.example.api.email.dto.EmailCreateDto;
import com.example.api.email.dto.EmailMetadataCreateDto;
import com.example.api.email.dto.EmailMetadataUpdateDto;
import com.example.api.email.dto.EmailRequestDto;
import com.example.core.entity.user.User;
import com.example.core.entity.email.EmailMetadata;
import com.example.core.exception.ResourceAlreadyExistException;
import com.example.core.exception.ResourceNotFoundException;
import com.example.core.repository.EmailMetadataRepository;
import com.example.api.user.UserService;
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

    public EmailMetadata loadEmailMetadataById(Long metadataId) {
        User requestUser = userService.loadUserFromSecurityContextHolder();
        Optional<EmailMetadata> emailMetadata = emailMetadataRepository.findByIdAndUser_Id(metadataId, requestUser.getId());
        if (emailMetadata.isPresent())
            return emailMetadata.get();
        else throw new ResourceNotFoundException("EmailMetadata not found with id: " + metadataId + " userId: " + requestUser.getId());
    }

    public EmailMetadata loadEmailMetadataByEmailAndUserId(String email, Long userId) {
        return emailMetadataRepository.findByEmailAndUser_Id(email, userId)
                .orElseThrow(() -> new ResourceNotFoundException("EmailMetadata not found by email & userId: " + email + userId));
    }

    public boolean emailMetadataExistsByEmailAndUserId(String email, Long userId) {
        return emailMetadataRepository.findByEmailAndUser_Id(email, userId).isPresent();
    }

    @Transactional(readOnly = false)
    public EmailMetadata createEmailMetadata(EmailMetadataCreateDto createDto) {
        // TODO: Java mail API 를 사용해서 유효한 메일 메타데이터 인지 검증해야함
        User requestUser = userService.loadUserFromSecurityContextHolder();
        if (emailMetadataExistsByEmailAndUserId(createDto.getEmail(), requestUser.getId())) {
            throw new ResourceAlreadyExistException("Resource already exist with info: " + createDto);
        } else return emailMetadataRepository.save(createDto.toEntity(requestUser));
    }

    @Transactional(readOnly = false)
    public EmailMetadata updateEmailMetadata(Long metadataId, EmailMetadataUpdateDto updateDto) {
        User requestUser = userService.loadUserFromSecurityContextHolder();
        EmailMetadata metadata = emailMetadataRepository.findByIdAndUser_Id(metadataId, requestUser.getId())
                .orElseThrow(NoSuchElementException::new);

        // TODO: MetadataUpdateDto의 위치를 바꿀것인지(api -> core)
        //   혹은 MetadataUpdateDto를 별도의 Entity로 바꾸어 처리할지
        metadata.update(updateDto.toUpdateEntity(metadataId));
        return emailMetadataRepository.save(metadata);
    }

    public void validMailMetadata(EmailRequestDto requestDto) {
        // 요청자 정보와 메일 정보 검증
        User requestUser = userService.loadUserFromSecurityContextHolder();
        EmailMetadata emailMetadata = loadEmailMetadataByEmailAndUserId(requestDto.getEmailFrom(), requestUser.getId());
        if (!emailMetadata.getUser().equals(requestUser)) {
            throw new IllegalArgumentException("EmailMetadata is not equal to request metadata: " + requestDto);
        }
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
