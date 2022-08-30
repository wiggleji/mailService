package com.example.mailService.repository;

import com.example.mailService.email.entity.EmailMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface EmailMetadataRepository extends JpaRepository<EmailMetadata, Long> {

    // userId 에 속한 모든 메일전송 정보 조회
    List<EmailMetadata> findAllByUser_Id(Long userId);

    Optional<EmailMetadata> findByIdAndUser_Id(Long metadataId, Long userId);

    // 메일 전송 시 email/userId 로 유효한 메일전송 정보 조회
    Optional<EmailMetadata> findByEmailAndUser_Id(String email, Long userId);
}
