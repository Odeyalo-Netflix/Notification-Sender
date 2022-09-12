package com.odeyalo.netflix.emailsenderservice.exceptions;

public class FileReadingFailedException extends RuntimeException {
    private final String userMessage;

    /**
     *
     * @param devMessage - message to developer
     * @param userMessage - message that will be returned to user
     */
    public FileReadingFailedException(String devMessage, String userMessage) {
        super(devMessage);
        this.userMessage = userMessage;
    }

    public FileReadingFailedException(String devMessage, String userMessage, Throwable cause) {
        this(devMessage, userMessage);
    }

    public String getUserMessage() {
        return userMessage;
    }
}
