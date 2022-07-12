package com.odeyalo.netflix.emailsenderservice.exceptions;

public class AccessTokenResolvingProcessException extends Exception {

    public AccessTokenResolvingProcessException() {
        super();
    }

    public AccessTokenResolvingProcessException(String message) {
        super(message);
    }

    public AccessTokenResolvingProcessException(String message, Throwable cause) {
        super(message, cause);
    }
}
