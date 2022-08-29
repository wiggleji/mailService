package com.example.mailService.email.entity;

import com.example.mailService.email.dto.EmailCreateDto;
import com.example.mailService.email.dto.EmailMetadataCreateDto;
import com.example.mailService.email.dto.EmailMetadataDto;
import com.example.mailService.email.dto.EmailMetadataUpdateDto;
import com.example.mailService.user.entity.BaseEntity;
import com.example.mailService.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email", "user_id"})
})
public class EmailMetadata extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    // TODO: 각 유저마다 email unique constraint
    private String email;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String smtpHost;

    @Column(nullable = false)
    private Long smtpPort;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void update(EmailMetadataUpdateDto updateDto) {
        this.email = updateDto.getEmail();
        this.username = updateDto.getUsername();
        this.password = updateDto.getPassword();
        this.smtpHost = updateDto.getSmtpHost();
        this.smtpPort = updateDto.getSmtpPort();
    }
}
