package com.example.core.repository;

import com.example.core.entity.email.Email;
import com.example.core.entity.email.EmailFolder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EmailRepository extends JpaRepository<Email, Long> {

    List<Email> findEmailsByUserId(Long userId);

    List<Email> findEmailsByEmailFolderAndDateTimeSend(EmailFolder emailFolder, LocalDateTime dateTimeSend);

    Optional<Email> findEmailById(Long emailId);

    Optional<Email> findEmailByIdAndUserId(Long emailId, Long userId);
}
