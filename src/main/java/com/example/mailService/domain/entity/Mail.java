package com.example.mailService.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Mail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String subject;

    private String content;

    private LocalDateTime dateTimeCreated;

    private LocalDateTime dateTimeSend;

    private String thread_id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User sender;

    @OneToMany(mappedBy = "mail")
    private List<MailMetadata> metadata;

    @PrePersist
    public void dateTimeCreated() {
        this.dateTimeCreated = LocalDateTime.now();
    }
}
