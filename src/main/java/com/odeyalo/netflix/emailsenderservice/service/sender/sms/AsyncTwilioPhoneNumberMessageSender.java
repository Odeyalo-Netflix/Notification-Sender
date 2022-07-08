package com.odeyalo.netflix.emailsenderservice.service.sender.sms;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AsyncTwilioPhoneNumberMessageSender implements PhoneNumberMessageSender {
    @Value("${twilio.phone.trial}")
    private String phoneNumber;

    @Override
    public void sendMessage(String to, String message) {
      Message.creator(new PhoneNumber(to), new PhoneNumber(phoneNumber), message).createAsync();
    }
}
