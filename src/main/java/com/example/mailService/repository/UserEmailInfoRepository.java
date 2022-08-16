package com.example.mailService.repository;

import com.example.mailService.domain.entity.UserEmailInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserEmailInfoRepository extends JpaRepository<UserEmailInfo, Long> {

    List<UserEmailInfo> findAllByUser(Long userId);
}
