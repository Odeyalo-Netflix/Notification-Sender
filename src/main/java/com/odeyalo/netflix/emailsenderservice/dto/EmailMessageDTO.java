package com.odeyalo.netflix.emailsenderservice.dto;

public class EmailMessageDTO {
    private String to;
    private String body;
    private String subject;

    public EmailMessageDTO() {
    }

    public EmailMessageDTO(String to, String body, String subject) {
        this.to = to;
        this.body = body;
        this.subject = subject;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
