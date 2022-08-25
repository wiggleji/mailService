package com.example.mailService.email;

import com.example.mailService.email.dto.EmailCreateDto;
import com.example.mailService.email.entity.Email;
import com.example.mailService.user.entity.User;
import com.example.mailService.exception.ResourceNotFoundException;
import com.example.mailService.repository.EmailRepository;
import com.example.mailService.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EmailService {

    private final EmailRepository emailRepository;

    private final UserService userService;

    public List<Email> loadEmailListByUserId() {
        User requestUser = userService.loadUserFromSecurityContextHolder();
        return emailRepository.findEmailsByUserId(requestUser.getId());
    }

    public Optional<Email> loadEmailByIdAndUserId(Long mailId) {
        User requestUser = userService.loadUserFromSecurityContextHolder();
        return emailRepository.findEmailByIdAndUserId(mailId, requestUser.getId());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Email loadEmailById(Long mailId) {
        // 어드민만 가능. 인증없이 메일 조회
        return emailRepository.findEmailById(mailId).orElseThrow(
                () -> new ResourceNotFoundException("Email not found with Id: " + mailId)
        );
    }

    @Transactional(readOnly = false)
    public Email createEmail(EmailCreateDto createDto) {
        User requestUser = userService.loadUserFromSecurityContextHolder();
        if (createDto.getUserId().equals(requestUser.getId())) {
            return emailRepository.save(createDto.toEntity());
        } else throw new IllegalArgumentException("Request user is not equal. User: " + requestUser.getId() + ", Request:" + createDto.getUserId());
    }

    @Transactional(readOnly = false)
    public void deleteEmail(Long emailId) {
        // 메일은 수정이 존재하지 않음.
        // 조회 / 생성 / 삭제만 존재.
        // 삭제는 데이터 삭제 대신 userId를 null 로 표현
        // https://www.baeldung.com/spring-jpa-soft-delete
        Optional<Email> email = loadEmailByIdAndUserId(emailId);
        // TODO: 메일 삭제 로직 실행
        // TODO: JPA repository 를 통해 soft delete 를 적용해줘야함
    }

}
