package com.example.mailService.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Slf4j
@Getter
@Builder
@Component
@AllArgsConstructor
public class MailUtils {

    private final String username;
    private final String password;
    private final String smtpHost;
    private final Long smtpPort;

    private final Properties properties;
    
    public Session getMailSession() {
        // 메일 전송을 위한 메일세션
        return Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    /**
     * 전송할 메일 메시지 instance 생성
     * @param session: javax.mail.Session 메일 전송에 사용될 세션
     * @param addressFrom: javax.mail.internet.InternetAddress 메일 전송자 주소
     * @param addressTo: javax.mail.Address 메일 수신자(to) 배열
     * @param addressCc: javax.mail.Address 메일 참조자(cc) 배열
     * @param addressBcc: javax.mail.Address 메일 숨은 참조자(bcc) 배열
     * @param subject: java.lang.String 메일 제목
     * @param text: java.lang.String 메일 본문
     * @return javax.mail.Message 메일 메시지 인스턴스
     */
    public Message generateMessage(
            Session session,
            InternetAddress addressFrom,
            Address[] addressTo,
            Address[] addressCc,
            Address[] addressBcc,
            String subject,
            String text
    ) {
        try {
            Message message = new MimeMessage(session);

            message.setFrom(addressFrom);
            message.setRecipients(Message.RecipientType.TO, addressTo);
            message.setRecipients(Message.RecipientType.CC, addressCc);
            message.setRecipients(Message.RecipientType.BCC, addressBcc);

            message.setSubject(subject);
            message.setText(text);

            return message;
        } catch (MessagingException e) {
            log.error("Error while creating message from session: " + session);
            throw new RuntimeException(e);
        }
    }

    /**
     * 메일 전송.
     * @param message: javax.mail.Message 전송할 메일 메시지
     */
    public void sendMessage(Message message) {
        try {
            Transport.send(message);
        } catch (MessagingException e) {
            log.error("Error while sending message: " + message);
            throw new RuntimeException(e);
        }
    }
}
