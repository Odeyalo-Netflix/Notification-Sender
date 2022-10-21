package com.odeyalo.netflix.emailsenderservice.service.support.events;

import org.springframework.beans.factory.annotation.Autowired;

public interface EventHandler {
    /**
     * Handle given event
     * @param event - event to process
     */
    void handleEvent(Event event);

    String type();

    @Autowired
    default void registryInContainer(EventHandlerRegistry registry) {
        registry.addEventHandler(type(), this);
    }
}
