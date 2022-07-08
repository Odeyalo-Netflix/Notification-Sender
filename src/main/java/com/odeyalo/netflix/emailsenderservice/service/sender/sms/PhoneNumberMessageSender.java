package com.odeyalo.netflix.emailsenderservice.service.sender.sms;

public interface PhoneNumberMessageSender {

    void sendMessage(String to, String message);

}
