package com.odeyalo.netflix.emailsenderservice.service.sender.email;

import javax.mail.MessagingException;

public interface EmailSenderManager {

    void send(String body, String subject, String to) throws MessagingException;

}
