package com.example.mailService.domain.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JwtTokenDto {
    private String token;
}
