package com.example.mailService.service;

import com.example.mailService.repository.UserEmailInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserEmailInfoService {

    private UserEmailInfoRepository userEmailInfoRepository;

    private UserService userService;

    // 유저 메일정보 조회 기능
}
