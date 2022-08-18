package com.example.mailService.domain.dto;

import com.example.mailService.domain.entity.UserEmailInfo;

import java.util.List;
import java.util.stream.Collectors;

public class UserEmailInfoListDto {
    List<UserEmailInfoDto> userEmailInfoDtoList;

    public static List<UserEmailInfoDto> from(List<UserEmailInfo> userEmailInfos) {
        return userEmailInfos.stream()
                .map(UserEmailInfoDto::from)
                .collect(Collectors.toList());
    }
}
