package com.example.mailService.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;

public class UserSignatureDto {

    @Builder
    @AllArgsConstructor
    public static class UserSignatureInfo {
        private Long id;

        private String signature;
    }
}
