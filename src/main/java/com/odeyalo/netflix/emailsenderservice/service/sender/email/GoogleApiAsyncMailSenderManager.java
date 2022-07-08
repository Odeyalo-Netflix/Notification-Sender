package com.odeyalo.netflix.emailsenderservice.service.sender.email;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public class GoogleApiAsyncMailSenderManager implements EmailSenderManager {

    @Override
    @Async
    public void send(String body, String subject, String to) throws MessagingException {
        //todo: Send mail using Google API
    }
}
