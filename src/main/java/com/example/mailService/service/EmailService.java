package com.example.mailService.service;

import com.example.mailService.domain.dto.EmailCreateDto;
import com.example.mailService.domain.entity.Email;
import com.example.mailService.domain.entity.User;
import com.example.mailService.exception.ResourceNotFoundException;
import com.example.mailService.repository.EmailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EmailService {

    private final EmailRepository emailRepository;

    private final UserService userService;

    public List<Email> loadEmailListByUserId(Long userId) {
        return emailRepository.findEmailsByUserId(userId);
    }

    public Email loadEmailById(Long mailId) throws ResourceNotFoundException {
        return emailRepository.findEmailById(mailId).orElseThrow(
                () -> new ResourceNotFoundException("Email not found with Id: " + mailId)
        );
    }

    public Email createEmail(EmailCreateDto createDto) {
        User requestUser = userService.loadUserFromSecurityContextHolder();
        if (createDto.getUserId().equals(requestUser.getId())) {
            return emailRepository.save(createDto.toEntity());
        } else throw new IllegalArgumentException("Request user is not equal. User: " + requestUser.getId() + ", Request:" + createDto.getUserId());
    }

    public void deleteEmail(Long emailId) {
        // 메일은 수정이 존재하지 않음.
        // 조회 / 생성 / 삭제만 존재.
        // 삭제는 데이터 삭제 대신 userId를 null 로 표현
        // https://www.baeldung.com/spring-jpa-soft-delete
        User requestUser = userService.loadUserFromSecurityContextHolder();
        Email email = loadEmailById(emailId);
        if (email.getId().equals(requestUser.getId())) {
            // TODO: 메일 삭제 로직 실행
            // TODO: JPA repository 를 통해 soft delete 를 적용해줘야함
        }
    }

}
