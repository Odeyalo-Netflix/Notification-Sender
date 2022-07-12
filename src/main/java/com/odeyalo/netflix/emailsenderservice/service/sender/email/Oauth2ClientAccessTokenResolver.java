package com.odeyalo.netflix.emailsenderservice.service.sender.email;

import com.odeyalo.netflix.emailsenderservice.exceptions.AccessTokenResolvingProcessException;

import java.nio.file.Path;

public interface Oauth2ClientAccessTokenResolver {

    /**
     * @param credentials - path to file with credentials
     * @see credentials.json example
     * @return - access token from oauth2 server
     */
    String getAccessToken(Path credentials) throws AccessTokenResolvingProcessException;

    String getAccessToken(String clientId, String clientSecret, String refreshToken) throws AccessTokenResolvingProcessException;
}
