package com.odeyalo.netflix.emailsenderservice.constrollers;

import com.odeyalo.netflix.emailsenderservice.dto.EmailMessageDTO;
import com.odeyalo.netflix.emailsenderservice.service.EmailSenderManager;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/api/v1")
public class EmailSenderController {
    private final EmailSenderManager senderManager;

    public EmailSenderController(EmailSenderManager senderManager) {
        this.senderManager = senderManager;
    }

    @Async
    @PostMapping("/send")
    public void sendMail(@RequestBody EmailMessageDTO dto) throws MessagingException {
        this.senderManager.send(dto.getBody(), dto.getSubject(), dto.getTo());
    }
}
