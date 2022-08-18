package com.example.mailService.email.entity;

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
public class EmailMetadata extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
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
}
