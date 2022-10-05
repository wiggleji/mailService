package com.example.mailService.email;

import com.example.core.entity.email.EmailMetadata;
import com.example.api.utils.Encryption;
import com.example.mailService.email.dto.EmailMessageDto;
import com.example.mailService.email.service.EmailMetadataService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Slf4j
@Getter
@Component
@AllArgsConstructor
public class EmailSender {

    private final EmailMetadataService metadataService;

    private final Encryption encryption;

    public Session generateMailSession(EmailMetadata metadata) {
        // 메일 전송을 위한 메일세션 생성
        Properties properties = metadataService.generateEmailMetadataProperty(metadata);
        return Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(metadata.getUsername(), encryption.decryptAES256(metadata.getPassword()));
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
    public Message generateMessage(
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

            return message;
        } catch (MessagingException e) {
            log.error("Error while creating message from session: " + session);
            throw new MessagingException(e.getMessage());
        }
    }

    /**
     * 메일 전송.
     * @param message: javax.mail.Message 전송할 메일 메시지
     */
    public void sendMessage(Message message) throws MessagingException {
        try {
            Transport.send(message);
        } catch (MessagingException e) {
            log.error("Error while sending message: " + message);
            throw new MessagingException(e.getMessage());
        }
    }

}
