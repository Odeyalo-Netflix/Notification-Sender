package com.odeyalo.netflix.emailsenderservice.service.sender.email;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public interface CachingEmailSender extends EmailSender {
    /**
     * Cache specific message that will be sent in future
     * @param message - message to cache
     */
    void cacheMessage(MimeMessage message);

    /**
     * Sends cached messages
     */
    void sendCachedMessage() throws MessagingException;

    /**
     * Clear cache of messages
     */
    void clearCache();
}
