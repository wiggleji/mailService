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

    @Transactional(readOnly = false)
    public Email createEmail(EmailCreateDto createDto) {
        User requestUser = userService.loadUserFromSecurityContextHolder();
        return emailRepository.save(createDto.toEntity(requestUser.getId()));
    }
}
