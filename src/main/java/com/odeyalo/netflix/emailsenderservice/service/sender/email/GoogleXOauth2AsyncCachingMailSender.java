package com.odeyalo.netflix.emailsenderservice.service.sender.email;

import com.odeyalo.netflix.emailsenderservice.exceptions.AccessTokenResolvingProcessException;
import com.odeyalo.netflix.emailsenderservice.service.support.events.EmailLetterSentEvent;
import com.odeyalo.netflix.emailsenderservice.service.support.events.EventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class GoogleXOauth2AsyncCachingMailSender implements CachingEmailSender {
    private final Oauth2ClientAccessTokenResolver accessTokenResolver;
    private final Session session;
    private final ConcurrentLinkedQueue<MimeMessage> cache = new ConcurrentLinkedQueue<>();
    private final Logger logger = LoggerFactory.getLogger(GoogleXOauth2AsyncCachingMailSender.class);
    private final EventPublisher eventPublisher;
    @Value("${app.mail.username}")
    private String username;
    @Value("${app.credentials.path}")
    private String GOOGLE_OAUTH2_CREDENTIALS_JSON_FILE_PATH;

    @Autowired
    public GoogleXOauth2AsyncCachingMailSender(@Qualifier("googleOauth2ClientAccessTokenResolver") Oauth2ClientAccessTokenResolver accessTokenResolver,
                                               Session session,
                                               EventPublisher eventPublisher) {
        this.accessTokenResolver = accessTokenResolver;
        this.session = session;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void cacheMessage(MimeMessage message) {
        this.cache.add(message);
        this.logger.info("Cached message with body: {}", message);
    }

    @Override
    @Scheduled(fixedDelay = 20000)
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
    public void send(MimeMessage message) throws MessagingException {
        try {
            this.logger.info("Starting email message sending to: {}", Arrays.toString(message.getAllRecipients()));
            String accessToken = this.accessTokenResolver.getAccessToken(Path.of(GOOGLE_OAUTH2_CREDENTIALS_JSON_FILE_PATH));
            Transport smtp = session.getTransport("smtp");
            smtp.connect(username, accessToken);
            smtp.sendMessage(message, message.getAllRecipients());
            this.logger.info("Successful sent message to: {}", Arrays.toString(message.getAllRecipients()));
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
            Transport smtp = session.getTransport("smtp");
            smtp.connect(username, accessToken);
            // Send messages until cache isn't empty
            MimeMessage mimeMessage = null;
            while (!(this.cache.isEmpty())) {
                try {
                    mimeMessage = cache.poll();
                    smtp.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
                    this.eventPublisher.publishEvent(EmailLetterSentEvent.EVENT_NAME, new EmailLetterSentEvent(mimeMessage, true));
                } catch (MessagingException ex) {
                    this.logger.error("Error during message sending.", ex);
                    this.eventPublisher.publishEvent(EmailLetterSentEvent.EVENT_NAME, new EmailLetterSentEvent(mimeMessage, ex));
                }
            }
        } catch (AccessTokenResolvingProcessException ex) {
            this.logger.error("Exception during access token resolving ", ex);
        }
    }
}
