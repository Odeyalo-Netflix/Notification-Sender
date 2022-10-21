package com.odeyalo.netflix.emailsenderservice.service.sender.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public interface EmailSender {

    void send(MimeMessage message) throws MessagingException;

}
