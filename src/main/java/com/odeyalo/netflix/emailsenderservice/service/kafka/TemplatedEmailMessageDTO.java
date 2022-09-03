package com.odeyalo.netflix.emailsenderservice.service.kafka;

import com.odeyalo.support.clients.notification.dto.EmailMessageDTO;

public class TemplatedEmailMessageDTO {
    private String to;
    private String subject;
    private String templateType;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }
}
