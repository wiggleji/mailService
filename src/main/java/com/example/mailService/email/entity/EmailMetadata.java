package com.example.mailService.email.entity;

import com.example.mailService.email.dto.EmailMetadataUpdateDto;
import com.example.mailService.user.entity.BaseEntity;
import com.example.mailService.user.entity.User;
import com.example.mailService.utils.Encryption;
import com.example.mailService.SpringContext;
import lombok.*;

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
        Encryption encryption = SpringContext.getBean(Encryption.class);

        this.email = updateDto.getEmail();
        this.username = updateDto.getUsername();
        this.password = encryption.encryptAES256(updateDto.getPassword());
        this.smtpHost = updateDto.getSmtpHost();
        this.smtpPort = updateDto.getSmtpPort();
    }
}
