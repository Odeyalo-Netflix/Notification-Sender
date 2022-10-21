package com.odeyalo.netflix.emailsenderservice.config;

import com.odeyalo.netflix.emailsenderservice.service.support.events.ConcurrentMapEventHandlerContainer;
import com.odeyalo.netflix.emailsenderservice.service.support.events.EventHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for EventHandlerRegistry interface.
 * It provides custom ConcurrentMapEventHandlerContainer configuration using constructor injection
 * @version 1.0.0
 * @see EventHandlerRegistry
 * @see ConcurrentMapEventHandlerContainer
 */
@Configuration
public class EventHandlerRegistryConfiguration {
    private final Logger logger = LoggerFactory.getLogger(EventHandlerRegistryConfiguration.class);

    @Bean
    @ConditionalOnMissingBean
    public EventHandlerRegistry eventHandlerRegistry() {
        return new ConcurrentMapEventHandlerContainer();
    }
}
