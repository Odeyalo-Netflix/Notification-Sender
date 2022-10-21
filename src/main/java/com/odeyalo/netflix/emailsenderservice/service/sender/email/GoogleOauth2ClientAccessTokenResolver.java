package com.odeyalo.netflix.emailsenderservice.service.sender.email;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.UserCredentials;
import com.odeyalo.netflix.emailsenderservice.exceptions.AccessTokenResolvingProcessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;

public class GoogleOauth2ClientAccessTokenResolver implements Oauth2ClientAccessTokenResolver {
    private final Logger logger = LoggerFactory.getLogger(GoogleOauth2ClientAccessTokenResolver.class);

    @Override
    public String getAccessToken(Path credentials) throws AccessTokenResolvingProcessException {
        try {
            this.logger.info("Resolve token from path: {}", credentials.toString());
            GoogleCredentials cred = GoogleCredentials.fromStream(new FileInputStream(credentials.toString()));
            cred.refreshIfExpired();
            return cred.getAccessToken().getTokenValue();
        } catch (IOException e) {
            this.logger.error("Error during resolving access token, the error is: {}, message: {}, stacktrace: {}", e.getClass(), e.getMessage(), e.getStackTrace());
            throw new AccessTokenResolvingProcessException("Error during resolving access token", e);
        }
    }

    @Override
    public String getAccessToken(String clientId, String clientSecret, String refreshToken) throws AccessTokenResolvingProcessException {
        try {
            UserCredentials cred = UserCredentials.newBuilder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .setRefreshToken(refreshToken)
                .build();
            cred.refreshIfExpired();
            return cred.getAccessToken().getTokenValue();
        } catch (IOException e) {
            this.logger.error("Error during resolving access token, the error is: {}", e.getClass());
            throw new AccessTokenResolvingProcessException("Error during resolving access token", e);
        }
    }
}
