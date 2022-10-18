package com.odeyalo.netflix.emailsenderservice.service.support.events.handlers;

import com.odeyalo.netflix.emailsenderservice.entity.SentEmail;
import com.odeyalo.netflix.emailsenderservice.repository.SentEmailRepository;
import com.odeyalo.netflix.emailsenderservice.service.support.events.EmailLetterSentEvent;
import com.odeyalo.netflix.emailsenderservice.service.support.events.Event;
import com.odeyalo.netflix.emailsenderservice.service.support.events.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Save email sending result to database
 * @version 1.0.0
 */
@Service
public class SaveSentResultEmailLetterSentEventHandler implements EventHandler {
    private final Logger logger = LoggerFactory.getLogger(SaveSentResultEmailLetterSentEventHandler.class);
    private final SentEmailRepository sentEmailRepository;
    public static final String EVENT_NAME = EmailLetterSentEvent.EVENT_NAME;

    @Autowired
    public SaveSentResultEmailLetterSentEventHandler(SentEmailRepository sentEmailRepository) {
        this.sentEmailRepository = sentEmailRepository;
    }

    @Override
    public void handleEvent(Event event) {
        this.logger.info("Event received: {}", event);
        if (!(event instanceof EmailLetterSentEvent)) {
            this.logger.error("Wrong event received, expected: {}, received: {}", EmailLetterSentEvent.class.getName(), event.getClass().getName());
            return;
        }
        EmailLetterSentEvent sentEvent = (EmailLetterSentEvent) event;
        SentEmail sentEmail = buildSentEmail(sentEvent);
        this.sentEmailRepository.save(sentEmail);
    }

    @Override
    public String type() {
        return EVENT_NAME;
    }


    protected SentEmail buildSentEmail(EmailLetterSentEvent sentEvent) {
        SentEmail sentEmail = SentEmail.builder()
                .body(sentEvent.getBody())
                .subject(sentEvent.getSubject())
                .to(sentEvent.getTo())
                .success(sentEvent.isSuccess())
                .build();
        if (!sentEvent.isSuccess()) {
            sentEmail.setExceptionReason(sentEvent.getExceptionReason());
        }
        return sentEmail;
    }
}
