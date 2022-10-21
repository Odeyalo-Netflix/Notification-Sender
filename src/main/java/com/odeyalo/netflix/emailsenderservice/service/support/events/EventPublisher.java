package com.odeyalo.netflix.emailsenderservice.service.support.events;

public interface EventPublisher {
    /**
     * Publish given event to application
     * @param type - type of event to publish
     * @param event - event info
     */
    void publishEvent(String type, Event event);

}
