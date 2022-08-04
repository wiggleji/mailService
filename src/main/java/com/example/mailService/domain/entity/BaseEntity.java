package com.example.mailService.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@Log4j2
@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class BaseEntity {
    // TODO: Jpa annotation 으로 변경
    // https://www.baeldung.com/database-auditing-jpa
    // https://wildeveloperetrain.tistory.com/76

    @Column(name = "CREATED_AT", updatable = false, nullable = false)
    private LocalDateTime createAt;

    @Column(name = "LAST_UPDATED_AT", nullable = false)
    private LocalDateTime lastUpdatedAt;

    @PrePersist
    public void prePersist() {
        log.info("entity prePersist");
        LocalDateTime now = LocalDateTime.now();

        createAt = now;
        lastUpdatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        log.info("entity preUpdate");
        lastUpdatedAt = LocalDateTime.now();
    }
}
