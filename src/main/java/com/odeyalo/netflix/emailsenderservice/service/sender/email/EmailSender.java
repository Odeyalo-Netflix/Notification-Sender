package com.odeyalo.netflix.emailsenderservice.service.sender.email;

import javax.mail.MessagingException;

public interface EmailSender {

    void send(String body, String subject, String to) throws MessagingException;

}
