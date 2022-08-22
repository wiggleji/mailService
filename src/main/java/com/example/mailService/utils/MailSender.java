package com.example.mailService.utils;

import com.example.mailService.email.dto.EmailMessageDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.util.Optional;
import java.util.Properties;

@Slf4j
@Getter
@Builder
@AllArgsConstructor
public class MailSender {

    private final String username;
    private final String password;
    private final String smtpHost;
    private final Long smtpPort;

    private final Properties properties;
    
    public Session generateMailSession() {
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
     *
     * @param session:         javax.mail.Session 메일 전송에 사용될 세션
     * @param emailMessageDto: com.example.mailService.email.dto.EmailMessageDto 메일 전소에 필요한 Java Mail API 데이터 형을 갖춘 DTO
     * @return javax.mail.Message 메일 메시지 인스턴스
     */
    public Optional<Message> generateMessage(
            Session session,
            EmailMessageDto emailMessageDto
    ) throws MessagingException {
        try {
            Message message = new MimeMessage(session);

            message.setFrom(emailMessageDto.getAddressFrom());
            message.setRecipients(Message.RecipientType.TO, emailMessageDto.getAddressTo());
            message.setRecipients(Message.RecipientType.CC, emailMessageDto.getAddressCc());
            message.setRecipients(Message.RecipientType.BCC, emailMessageDto.getAddressBcc());

            message.setSubject(emailMessageDto.getSubject());
            message.setText(emailMessageDto.getText());

            return Optional.of(message);
        } catch (MessagingException e) {
            log.error("Error while creating message from session: " + session);
            throw new MessagingException(e.getMessage());
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
