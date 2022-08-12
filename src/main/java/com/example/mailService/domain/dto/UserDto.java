package com.example.mailService.domain.dto;

import com.example.mailService.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserDto {

    private String username;

    private String email;

    public static UserDto from(User user) {
        // Entity to DTO
        return new UserDto((user.getUsername()), user.getEmail());
    }
}
