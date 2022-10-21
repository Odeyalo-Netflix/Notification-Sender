package com.odeyalo.netflix.emailsenderservice.config;

import com.odeyalo.netflix.emailsenderservice.exceptions.AccessTokenResolvingProcessException;
import com.odeyalo.netflix.emailsenderservice.service.sender.email.GoogleOauth2ClientAccessTokenResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.nio.file.Path;

/**
 * Support class to check and resolve oauth2 tokens
 */
@Configuration
public class Oauth2TokensConfigurationSupport {
    private final String GOOGLE_OAUTH2_CREDENTIALS_JSON_FILE_PATH;
    private final Logger logger = LoggerFactory.getLogger(Oauth2TokensConfigurationSupport.class);

    @Autowired
    public Oauth2TokensConfigurationSupport(@Value("${app.credentials.path}") String path) {
        GOOGLE_OAUTH2_CREDENTIALS_JSON_FILE_PATH = path;
    }

    @Bean
    @Profile("dev")
    public Void checkGoogleRefreshToken(GoogleOauth2ClientAccessTokenResolver googleOauth2ClientAccessTokenResolver) throws AccessTokenResolvingProcessException {
        googleOauth2ClientAccessTokenResolver.getAccessToken(Path.of(GOOGLE_OAUTH2_CREDENTIALS_JSON_FILE_PATH));
        this.logger.info("Current refresh token from path: {} is valid", GOOGLE_OAUTH2_CREDENTIALS_JSON_FILE_PATH);
        return null;
    }


    @Bean
    public GoogleOauth2ClientAccessTokenResolver googleOauth2ClientAccessTokenResolver() {
        return new GoogleOauth2ClientAccessTokenResolver();
    }
}
