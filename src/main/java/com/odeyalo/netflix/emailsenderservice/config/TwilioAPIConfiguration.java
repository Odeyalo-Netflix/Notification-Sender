package com.odeyalo.netflix.emailsenderservice.config;

import com.twilio.Twilio;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioAPIConfiguration {
    @Value("${twilio.account.sid}")
    private String accountSid;
    @Value("${twilio.account.auth.token}")
    private String authToken;

    @Bean
    public void init() {
        Twilio.init(accountSid, authToken);
    }
}
