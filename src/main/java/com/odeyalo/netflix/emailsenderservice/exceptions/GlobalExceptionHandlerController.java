package com.odeyalo.netflix.emailsenderservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.mail.MessagingException;

@RestControllerAdvice
public class GlobalExceptionHandlerController {

    @ExceptionHandler(value = MessagingException.class)
    public ResponseEntity<?> handleMessagingException(MessagingException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
