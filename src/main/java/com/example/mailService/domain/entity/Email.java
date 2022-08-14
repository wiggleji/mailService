package com.example.mailService.domain.entity;

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

    @Column(nullable = false, updatable = false)
    private Long userId;

    @Column(nullable = false, updatable = false)
    private String emailFrom;

    @Column(nullable = false, updatable = false)
    private String emailTo;

    @Column(nullable = false, updatable = false)
    private String subject;

    @Column(updatable = false)
    private String content;

    @Column(updatable = false)
    private LocalDateTime dateTimeSend;

    private LocalDateTime dateTimeReceive;
}