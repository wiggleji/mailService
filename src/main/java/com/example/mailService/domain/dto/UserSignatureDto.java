package com.example.mailService.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class UserSignatureDto {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class UserSignatureInfoDto {
        private Long id;

        private String signature;
    }
}
