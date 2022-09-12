package com.odeyalo.netflix.emailsenderservice.service.support;

import javax.mail.internet.MimeMessage;

/**
 * Cache the incoming message to CachingEmailSender
 * @see com.odeyalo.netflix.emailsenderservice.service.sender.email.CachingEmailSender
 */
public interface IncomingEmailMessageCachingService {
    /**
     * Cache the given message
     * @param message - message to cache
     * @return - true if message was added to cache successfully
     */
    boolean cache(MimeMessage message);
}
