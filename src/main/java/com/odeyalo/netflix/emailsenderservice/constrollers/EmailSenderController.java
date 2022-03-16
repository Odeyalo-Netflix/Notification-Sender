package com.odeyalo.netflix.emailsenderservice.constrollers;

import com.odeyalo.netflix.emailsenderservice.dto.EmailMessageDTO;
import com.odeyalo.netflix.emailsenderservice.service.EmailSenderManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/send")
    public ResponseEntity<?> sendMail(@RequestBody EmailMessageDTO dto) throws MessagingException {
        this.senderManager.send(dto.getBody(), dto.getSubject(), dto.getTo());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
