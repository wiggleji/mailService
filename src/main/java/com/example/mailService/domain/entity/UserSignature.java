package com.example.mailService.domain.entity;

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
public class UserSignature {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String signature;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
