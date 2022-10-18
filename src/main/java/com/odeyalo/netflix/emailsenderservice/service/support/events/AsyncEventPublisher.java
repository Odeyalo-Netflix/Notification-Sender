package com.odeyalo.netflix.emailsenderservice.service.support.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * EventPublisher implementation that support asynchronous event execution
 */
@Component
public class AsyncEventPublisher implements EventPublisher {
    private final Logger logger = LoggerFactory.getLogger(AsyncEventPublisher.class);
    private final EventHandlerRegistry container;

    @Autowired
    public AsyncEventPublisher(EventHandlerRegistry container) {
        this.container = container;
    }

    @Override
    @Async
    public void publishEvent(String type, Event event) {
        List<EventHandler> handlers = this.container.getHandler(type);
        for (EventHandler handler : handlers) {
            handler.handleEvent(event);
        }
    }
}
