package com.odeyalo.netflix.emailsenderservice.service.support.events;

import com.odeyalo.netflix.emailsenderservice.service.support.events.handlers.SaveSentResultEmailLetterSentEventHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.mail.internet.MimeMessage;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class EmailLetterSentEvent extends Event {
    private String to;
    private String body;
    private String subject;
    private boolean success;
    private String exceptionReason;
    public final static String EVENT_NAME = "EMAIL_LETTER_SENT_EVENT";

    public EmailLetterSentEvent(MimeMessage message, boolean success) {
        try {
            this.to = message.getAllRecipients()[0].toString();
            this.body = (String) message.getContent();
            this.subject = message.getSubject();
            this.success = success;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public EmailLetterSentEvent(MimeMessage message, Throwable exception) {
        this(message, false);
        this.exceptionReason = exception.getMessage();

    }
}
