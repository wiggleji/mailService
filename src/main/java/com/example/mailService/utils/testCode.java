package com.example.mailService.utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class testCode {

    public static void main(String[] args) {
        String username = "id";
        String password = "password";
        String from = "test@test.com";
        String to = "roamgom@gmail.com";

        String host = "smtp.naver.com";
        int port = 465;

        // SMTP Properties
        // https://javaee.github.io/javamail/docs/api/com/sun/mail/smtp/package-summary.html
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.trust", "smtp.naver.com");

        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));

            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

            message.setSubject("Java mail test");
            message.setText("email testing for mailService");

            Transport.send(message);

            System.out.println("Mail sent done");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }
}
