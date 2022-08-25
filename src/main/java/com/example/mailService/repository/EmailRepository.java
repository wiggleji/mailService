package com.example.mailService.repository;

import com.example.mailService.email.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmailRepository extends JpaRepository<Email, Long> {

    List<Email> findEmailsByUserId(Long userId);

    Optional<Email> findEmailById(Long emailId);

    Optional<Email> findEmailByIdAndUserId(Long emailId, Long userId);
}
