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
public class MailMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CarbonCopy carbonCopy;

    private LocalDateTime dateTimeCreated;

    @ManyToOne
    @JoinColumn(name = "mail_id")
    private Mail mail;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User receiver;

    @PrePersist
    public void dateTimeCreated() {
        this.dateTimeCreated = LocalDateTime.now();
    }
}
