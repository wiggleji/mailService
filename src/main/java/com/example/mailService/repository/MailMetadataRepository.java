package com.example.mailService.repository;

import com.example.mailService.domain.entity.MailMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailMetadataRepository extends JpaRepository<MailMetadata, Long> {
}
