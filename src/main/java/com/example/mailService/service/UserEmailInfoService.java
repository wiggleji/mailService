package com.example.mailService.service;

import com.example.mailService.domain.dto.UserEmailInfoCreateDto;
import com.example.mailService.domain.dto.UserEmailInfoDto;
import com.example.mailService.domain.dto.UserEmailInfoListDto;
import com.example.mailService.domain.entity.User;
import com.example.mailService.domain.entity.UserEmailInfo;
import com.example.mailService.exception.ResourceAlreadyExistException;
import com.example.mailService.exception.ResourceNotFoundException;
import com.example.mailService.repository.UserEmailInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserEmailInfoService {

    private final UserEmailInfoRepository userEmailInfoRepository;

    private final UserService userService;

    // 유저 메일정보 조회 / 생성

    public List<UserEmailInfo> loadUserEmailInfoListByUserId(Long userId) {
        return userEmailInfoRepository.findAllByUser_Id(userId);
    }

    public UserEmailInfo loadUserEmailInfoById(Long userEmailInfoId) {
        return userEmailInfoRepository.findById(userEmailInfoId)
                .orElseThrow(() -> new ResourceNotFoundException("UserEmailInfo not found by id: " + userEmailInfoId));
    }

    public UserEmailInfo createUserEmailInfo(UserEmailInfoCreateDto createDto) {
        User requestUser = userService.loadUserFromSecurityContextHolder();
        Optional<UserEmailInfo> existingUserEmailInfo = userEmailInfoRepository.findByEmailAndUser_Id(createDto.getEmail(), requestUser.getId());
        if (!existingUserEmailInfo.isPresent() & createDto.getUser().equals(requestUser)) {
            return userEmailInfoRepository.save(createDto.toEntity());
        } else if (existingUserEmailInfo.isPresent()) {
            throw new ResourceAlreadyExistException("Resource already exist with: " + existingUserEmailInfo);
        } else throw new IllegalArgumentException("Request user is not equal. User: " + requestUser.getId() + ", Request:" + createDto.getUser().getId());
    }
}
