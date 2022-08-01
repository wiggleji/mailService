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
public class MailFile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // TODO: change to Enum
    // 허용된 파일타입을 미리 지정해두기
    // HTTP MIME type 으로 명시: https://developer.mozilla.org/ko/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Common_types
    private String fileType;

    // TODO: AWS S3 적용
    // 파일 업로드는 AWS S3 사용
    private String fileUrl;

    @ManyToOne
    @JoinColumn(name = "mail_id")
    private Mail mail;
}
