package com.example.mailService.repository;

import com.example.mailService.domain.entity.User;
import com.example.mailService.domain.entity.UserEmailInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserEmailInfoRepository extends JpaRepository<UserEmailInfo, Long> {

    // userId 에 속한 모든 메일전송 정보 조회
    List<UserEmailInfo> findAllByUser_Id(Long userId);

    // 메일 전송 시 email/userId 로 유효한 메일전송 정보 조회
    Optional<UserEmailInfo> findByEmailAndUser_Id(String email, Long userId);
}
