package com.odeyalo.netflix.emailsenderservice.service.sender.email;

import com.odeyalo.netflix.emailsenderservice.exceptions.AccessTokenResolvingProcessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import java.nio.file.Paths;

@Service
public class GoogleXOauth2AsyncMailSender implements EmailSender {
    private final Oauth2ClientAccessTokenResolver accessTokenResolver;
    private final Session session;
    @Value("${app.credentials.path}")
    private String GOOGLE_OAUTH2_CREDENTIALS_JSON_FILE_PATH;
    private final Logger logger = LoggerFactory.getLogger(GoogleXOauth2AsyncMailSender.class);

    @Autowired
    public GoogleXOauth2AsyncMailSender(@Qualifier("googleOauth2ClientAccessTokenResolver") Oauth2ClientAccessTokenResolver accessTokenResolver, Session session) {
        this.accessTokenResolver = accessTokenResolver;
        this.session = session;
    }

    @Override
    @Async
    public void send(String body, String subject, String to) throws MessagingException {
        String username = "theprogrammerinfuture@gmail.com";
        try {
            String accessToken = this.accessTokenResolver.getAccessToken(Paths.get(GOOGLE_OAUTH2_CREDENTIALS_JSON_FILE_PATH));
            MimeMessage message = new MimeMessage(session);
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setText(body);
            helper.setSubject(subject);
            helper.setTo(to);
            Transport smtp = session.getTransport("smtp");
            smtp.connect(username, accessToken);
            smtp.sendMessage(message, message.getAllRecipients());
        } catch (AccessTokenResolvingProcessException e) {
            this.logger.error("Cannot resolve access token. Error message: {}, stacktrace: {}", e.getMessage(), e.getStackTrace());
            throw new MessagingException("Error during sending message. Error code: 601. Please try again later");
        }
    }
}
