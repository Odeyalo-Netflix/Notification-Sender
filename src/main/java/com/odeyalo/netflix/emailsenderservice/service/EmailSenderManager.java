package com.odeyalo.netflix.emailsenderservice.service;

public interface EmailSenderManager {

    void send(String body, String subject, String to);

}
