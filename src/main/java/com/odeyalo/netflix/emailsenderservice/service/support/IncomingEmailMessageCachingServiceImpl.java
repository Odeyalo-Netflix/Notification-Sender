package com.odeyalo.netflix.emailsenderservice.service.support;

import com.odeyalo.netflix.emailsenderservice.service.sender.email.CachingEmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

@Component
public class IncomingEmailMessageCachingServiceImpl implements IncomingEmailMessageCachingService {
    private final CachingEmailSender sender;

    @Autowired
    public IncomingEmailMessageCachingServiceImpl(CachingEmailSender sender) {
        this.sender = sender;
    }

    @Override
    public boolean cache(MimeMessage message) {
        this.sender.cacheMessage(message);
        return true;
    }
}
