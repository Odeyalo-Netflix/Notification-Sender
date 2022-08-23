package com.odeyalo.netflix.emailsenderservice.service.sender.email;


import com.odeyalo.support.clients.notification.dto.EmailMessageDTO;

import javax.mail.MessagingException;

public interface CachingEmailSender extends EmailSender {
    /**
     * Cache specific message that will be sended in future
     * @param message - message to cache
     */
    void cacheMessage(EmailMessageDTO message);

    /**
     * Sends cached messages
     */
    void sendCachedMessage() throws MessagingException;

    /**
     * Clear cache of messages
     */
    void clearCache();
}
