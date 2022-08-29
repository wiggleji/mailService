package com.example.mailService.email.entity;

import com.example.mailService.user.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Email extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String emailFrom;

    @Column(nullable = false)
    private String emailTo;

    private String emailToList;

    private String emailCcList;

    private String emailBccList;

    @Column(nullable = false)
    private String subject;

    private String text;

    private LocalDateTime dateTimeSend;

    private LocalDateTime dateTimeReceive;


    @PrePersist
    public void prePersist() {
        if (this.dateTimeSend == null) {
            // 메일 즉시 전송 시 송신/수신 시간은 동일
            this.dateTimeSend = LocalDateTime.now();
            this.dateTimeReceive = LocalDateTime.now();
        }
    }
}
