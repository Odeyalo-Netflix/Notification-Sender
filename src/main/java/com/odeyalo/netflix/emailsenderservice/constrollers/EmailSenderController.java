package com.odeyalo.netflix.emailsenderservice.constrollers;

import com.odeyalo.netflix.emailsenderservice.service.sender.email.CachingEmailSender;
import com.odeyalo.netflix.emailsenderservice.service.support.MimeMessageBuilder;
import com.odeyalo.support.clients.notification.dto.EmailMessageDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
@RequestMapping("/api/v1")
public class EmailSenderController {
    private final CachingEmailSender sender;
    private final MimeMessageBuilder mimeMessageBuilder;

    public EmailSenderController(@Qualifier("googleXOauth2AsyncCachingMailSender") CachingEmailSender sender, MimeMessageBuilder mimeMessageBuilder) {
        this.sender = sender;
        this.mimeMessageBuilder = mimeMessageBuilder;
    }

    @PostMapping("/send")
    public void sendMail(@RequestBody EmailMessageDTO dto) throws MessagingException, IOException, GeneralSecurityException {
        MimeMessage message = this.mimeMessageBuilder.buildMimeMessage(dto);
        this.sender.cacheMessage(message);
    }
}
