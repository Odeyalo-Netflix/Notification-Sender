package com.odeyalo.netflix.emailsenderservice.config;

import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.mail.Session;
import java.util.Map;
import java.util.Properties;

@Configuration
public class EmailConfiguration {
    @Bean
    public Session session(MailProperties mailProperties) {
        Map<String, String> map = mailProperties.getProperties();
        Properties properties = new Properties();
        properties.putAll(map);
        return Session.getDefaultInstance(properties);
    }
}
