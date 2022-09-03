package com.odeyalo.netflix.emailsenderservice.service.sender.email;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @deprecated Do not use this sender, since Google turned off login to account using Java Mail API. You can use:
 * @see GoogleXOauth2AsyncCachingMailSender
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
    public void send(MimeMessage message) throws MessagingException {
        this.mailSender.send(message);
    }
}
