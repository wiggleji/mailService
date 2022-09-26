package com.example.api.user.dto;

import com.example.core.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserDto {

    private Long id;

    private String username;

    private String email;

    public static UserDto from(User user) {
        // Entity to DTO
        return new UserDto(user.getId(), user.getUsername(), user.getEmail());
    }
}
