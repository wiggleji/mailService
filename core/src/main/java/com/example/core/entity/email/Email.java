package com.example.core.entity.email;

import com.example.core.entity.user.BaseEntity;
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
    @Enumerated(EnumType.STRING)
    private EmailFolder emailFolder;

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

    public void updateEmailFolder(EmailFolder emailFolder) {
        this.emailFolder = emailFolder;
    }
}
