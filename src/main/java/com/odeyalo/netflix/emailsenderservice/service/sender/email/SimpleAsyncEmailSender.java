package com.odeyalo.netflix.emailsenderservice.service.sender.email;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;

/**
 * @deprecated Do not use this sender, since Google turned off login to account using Java Mail API. You can use:
 * @see GoogleXOauth2AsyncMailSender
 * Use it only for email service that not required oauth2(such Yandex mail)
 */
@Component
@Deprecated
public class SimpleAsyncEmailSender implements EmailSender {
    private final JavaMailSender mailSender;

    public SimpleAsyncEmailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    @Override
    public void send(String body, String subject, String to) throws MessagingException {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(body);
        this.mailSender.send(mailMessage);
    }
}
