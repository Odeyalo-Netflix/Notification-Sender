package com.odeyalo.netflix.emailsenderservice.service.support;

import com.odeyalo.netflix.emailsenderservice.service.kafka.TemplatedEmailMessageDTO;
import com.odeyalo.support.clients.notification.dto.EmailMessageDTO;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

@Component
public class MimeMessageBuilder {
    private final Session session;

    public MimeMessageBuilder(Session session) {
        this.session = session;
    }

    public MimeMessage buildMimeMessage(EmailMessageDTO dto) throws MessagingException {
        MimeMessage message = new MimeMessage(session);
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setTo(dto.getTo());
        helper.setText(dto.getBody());
        helper.setSubject(dto.getSubject());
        return message;
    }

    public MimeMessage buildMimeMessage(TemplatedEmailMessageDTO dto, String templateBody) throws MessagingException {
        MimeMessage message = new MimeMessage(session);
        message.setContent(templateBody, "text/html; charset=utf-8");
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setTo(dto.getTo());
        helper.setSubject(dto.getSubject());
        return message;
    }
}
