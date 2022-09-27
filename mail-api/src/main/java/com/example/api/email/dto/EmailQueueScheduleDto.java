package com.example.api.email.dto;

import com.example.core.entity.email.Email;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Getter
@Builder
public class EmailQueueScheduleDto {

    private Long id;

    private LocalDateTime dateTimeSend;

    public static EmailQueueScheduleDto fromEmailEntity(Email email) {
        return EmailQueueScheduleDto.builder()
                .id(email.getId())
                .dateTimeSend(email.getDateTimeSend())
                .build();
    }

    public String getStringUuid() {
        return (this.id.toString() + dateTimeSend.toInstant(ZoneOffset.ofTotalSeconds(0)).toString());
    }
}
