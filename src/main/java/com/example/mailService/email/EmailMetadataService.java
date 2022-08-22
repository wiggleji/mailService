package com.example.mailService.email;

import com.example.mailService.email.dto.MailMetadataCreateDto;
import com.example.mailService.user.entity.User;
import com.example.mailService.email.entity.EmailMetadata;
import com.example.mailService.exception.ResourceAlreadyExistException;
import com.example.mailService.exception.ResourceNotFoundException;
import com.example.mailService.repository.UserEmailInfoRepository;
import com.example.mailService.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class EmailMetadataService {

    private final UserEmailInfoRepository userEmailInfoRepository;

    private final UserService userService;

    // 유저 메일정보 조회 / 생성

    public List<EmailMetadata> loadEmailMetadataListByUserId(Long userId) {
        return userEmailInfoRepository.findAllByUser_Id(userId);
    }

    public EmailMetadata loadEmailMetadataById(Long metadataId) {
        return userEmailInfoRepository.findById(metadataId)
                .orElseThrow(() -> new ResourceNotFoundException("EmailMetadata not found by id: " + metadataId));
    }

    public EmailMetadata loadEmailMetadataByEmailAndUserId(String email, Long userId) {
        return userEmailInfoRepository.findByEmailAndUser_Id(email, userId)
                .orElseThrow(() -> new ResourceNotFoundException("EmailMetadata not found by email & userId: " + email + userId));
    }

    public EmailMetadata createEmailMetadata(MailMetadataCreateDto createDto) {
        // TODO: Java mail API 를 사용해서 유효한 메일 메타데이터 인지 검증해야함
        User requestUser = userService.loadUserFromSecurityContextHolder();
        Optional<EmailMetadata> existingUserEmailInfo = userEmailInfoRepository.findByEmailAndUser_Id(createDto.getEmail(), requestUser.getId());
        if (!existingUserEmailInfo.isPresent() & createDto.getUser().equals(requestUser)) {
            return userEmailInfoRepository.save(createDto.toEntity());
        } else if (existingUserEmailInfo.isPresent()) {
            throw new ResourceAlreadyExistException("Resource already exist with: " + existingUserEmailInfo);
        } else throw new IllegalArgumentException("Request user is not equal. User: " + requestUser.getId() + ", Request:" + createDto.getUser().getId());
    }
}
