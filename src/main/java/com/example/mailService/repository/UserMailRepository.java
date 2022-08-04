package com.example.mailService.repository;

import com.example.mailService.domain.entity.UserMail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMailRepository extends JpaRepository<UserMail, Long> {
}
