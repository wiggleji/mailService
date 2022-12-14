package com.example.api.email;

import com.example.api.email.dto.EmailRequestDto;
import com.example.core.entity.email.Email;
import com.example.core.entity.email.EmailFolder;
import com.example.core.entity.user.User;
import com.example.core.exception.ResourceNotFoundException;
import com.example.core.repository.EmailRepository;
import com.example.api.user.UserService;
import com.example.core.service.CoreEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EmailWithUserContextService extends CoreEmailService {

    private final EmailRepository emailRepository;

    private final UserService userService;

    public List<Email> loadEmailListByUserId() {
        User requestUser = userService.loadUserFromSecurityContextHolder();
        return emailRepository.findEmailsByUserId(requestUser.getId());
    }

    public Email loadEmailByIdAndUserId(Long emailId) {
        User requestUser = userService.loadUserFromSecurityContextHolder();
        Optional<Email> email = emailRepository.findEmailByIdAndUserId(emailId, requestUser.getId());
        if (email.isPresent()) {
            return email.get();
        } else throw new ResourceNotFoundException("Email not found with id: " + emailId + " userId: " + requestUser.getId());
    }

    @Transactional(readOnly = false)
    public Email createScheduledEmail(EmailRequestDto requestDto) {
        User requestUser = userService.loadUserFromSecurityContextHolder();
        return emailRepository.save(requestDto.toScheduledEmailEntity(requestUser.getId()));
    }
}
