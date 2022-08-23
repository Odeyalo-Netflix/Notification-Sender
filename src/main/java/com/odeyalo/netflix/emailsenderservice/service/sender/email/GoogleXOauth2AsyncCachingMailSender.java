package com.odeyalo.netflix.emailsenderservice.service.sender.email;

import com.odeyalo.netflix.emailsenderservice.exceptions.AccessTokenResolvingProcessException;
import com.odeyalo.support.clients.notification.dto.EmailMessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class GoogleXOauth2AsyncCachingMailSender implements CachingEmailSender {
    private final Oauth2ClientAccessTokenResolver accessTokenResolver;
    private final Session session;
    private final ConcurrentLinkedQueue<EmailMessageDTO> cache = new ConcurrentLinkedQueue<>();
    private final Logger logger = LoggerFactory.getLogger(GoogleXOauth2AsyncCachingMailSender.class);
    @Value("${app.mail.username}")
    private String username;
    @Value("${app.credentials.path}")
    private String GOOGLE_OAUTH2_CREDENTIALS_JSON_FILE_PATH;

    @Autowired
    public GoogleXOauth2AsyncCachingMailSender(@Qualifier("googleOauth2ClientAccessTokenResolver") Oauth2ClientAccessTokenResolver accessTokenResolver, Session session) {
        this.accessTokenResolver = accessTokenResolver;
        this.session = session;
    }

    @Override
    public void cacheMessage(EmailMessageDTO message) {
        this.cache.add(message);
        this.logger.info("Cached message with body: {}", message);
    }

    @Override
    @Scheduled(fixedDelay = 60000)
    public void sendCachedMessage() throws MessagingException {
        if (cache.isEmpty()) {
            this.logger.info("Message cache is empty. Skipped");
            return;
        }
        this.logger.info("Starting sending {} messages from cache", cache.size());
        doSendCachedMessage();
        clearCache();
    }

    @Override
    @Async
    public void send(String body, String subject, String to) throws MessagingException {
        try {
            this.logger.info("Starting email message sending to: {}", to);
            String accessToken = this.accessTokenResolver.getAccessToken(Paths.get(GOOGLE_OAUTH2_CREDENTIALS_JSON_FILE_PATH));
            MimeMessage message = new MimeMessage(session);
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setText(body);
            helper.setSubject(subject);
            helper.setTo(to);
            Transport smtp = session.getTransport("smtp");
            smtp.connect(username, accessToken);
            smtp.sendMessage(message, message.getAllRecipients());
            this.logger.info("Successful sent message to: {}", to);
        } catch (AccessTokenResolvingProcessException e) {
            this.logger.error("Cannot resolve access token. Error message: {}, stacktrace: {}", e.getMessage(), e.getStackTrace());
            throw new MessagingException("Error during sending message. Error code: 601. Please try again later");
        }
    }

    @Override
    public void clearCache() {
        this.cache.clear();
        this.logger.info("Cleared cache");
    }

    protected void doSendCachedMessage() throws MessagingException {
        try {
            String accessToken = this.accessTokenResolver.getAccessToken(Paths.get(GOOGLE_OAUTH2_CREDENTIALS_JSON_FILE_PATH));
            MimeMessage mimeMessage = new MimeMessage(session);
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            Transport smtp = session.getTransport("smtp");
            smtp.connect(username, accessToken);
            // Send messages while cache isn't empty
            while (!(this.cache.isEmpty())) {
                try {
                    EmailMessageDTO message = this.cache.poll();
                    String body = message.getBody();
                    String subject = message.getSubject();
                    String to = message.getTo();
                    helper.setText(body);
                    helper.setSubject(subject);
                    helper.setTo(to);
                    mimeMessage.saveChanges();
                    smtp.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
                    this.logger.info("Sent message to: {} with body {} by {}", to, body, subject);
                } catch (MessagingException ex) {
                    this.logger.error("Error during message sending.", ex);
                }

            }
        } catch (AccessTokenResolvingProcessException ex) {
            this.logger.error("Exception during access token resolving ", ex);
        }
    }
}
