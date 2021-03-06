package com.odeyalo.netflix.emailsenderservice.constrollers;

import com.odeyalo.netflix.emailsenderservice.dto.EmailMessageDTO;
import com.odeyalo.netflix.emailsenderservice.service.sender.email.GoogleXOauth2AsyncMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
@RequestMapping("/api/v1")
public class EmailSenderController {
    private final GoogleXOauth2AsyncMailSender sender;

    public EmailSenderController(GoogleXOauth2AsyncMailSender sender) {
        this.sender = sender;
    }

    @PostMapping("/send")
    public void sendMail(@RequestBody EmailMessageDTO dto) throws MessagingException, IOException, GeneralSecurityException {
        this.sender.send(dto.getBody(), dto.getSubject(), dto.getTo());
    }
}
