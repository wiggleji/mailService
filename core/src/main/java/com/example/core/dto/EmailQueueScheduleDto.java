package com.example.core.dto;

import com.example.core.entity.email.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailQueueScheduleDto {

    private Long id;

    private LocalDateTime dateTimeSend;

    public static EmailQueueScheduleDto from(Email email) {
        return EmailQueueScheduleDto.builder()
                .id(email.getId())
                .dateTimeSend(email.getDateTimeSend())
                .build();
    }

    public String getStringUuid() {
        return (this.id.toString() + "--" + dateTimeSend.toInstant(ZoneOffset.ofTotalSeconds(0)).toString());
    }
}
