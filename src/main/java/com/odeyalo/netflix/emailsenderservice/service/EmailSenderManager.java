package com.odeyalo.netflix.emailsenderservice.service;

import javax.mail.MessagingException;

public interface EmailSenderManager {

    void send(String body, String subject, String to) throws MessagingException;

}
