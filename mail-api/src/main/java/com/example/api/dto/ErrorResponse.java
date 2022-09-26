package com.example.api.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class ErrorResponse {
    private String errorMessage;
    private LocalDateTime datetimeResponse;
}
