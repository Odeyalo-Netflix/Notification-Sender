package com.odeyalo.netflix.emailsenderservice.service.support.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentMapEventHandlerContainer implements EventHandlerRegistry {
    private final ConcurrentHashMap<String, List<EventHandler>> eventHandlers;
    private final Logger logger = LoggerFactory.getLogger(ConcurrentMapEventHandlerContainer.class);

    public ConcurrentMapEventHandlerContainer() {
        this.eventHandlers = new ConcurrentHashMap<>();
    }

    public ConcurrentMapEventHandlerContainer(ConcurrentHashMap<String, List<EventHandler>> eventHandlers) {
        this.eventHandlers = eventHandlers;
        this.logger.info("ConcurrentMapEventHandlerContainer was initialized with given map, map size: {}. Enable debug mode to show added handlers", eventHandlers.size());
        if (logger.isDebugEnabled()) {
            eventHandlers.forEach((type, handlers) -> {
                for (EventHandler handler : handlers) {
                    this.logger.debug("Type: {}, handler: {}", type, handler);
                }
            });
        }
    }

    @Override
    public void addEventHandler(String type, EventHandler handler) {
        List<EventHandler> handlers = this.eventHandlers.computeIfAbsent(type, k -> new ArrayList<>());
        handlers.add(handler);
        this.eventHandlers.put(type, handlers);
        this.logger.info("Added to handlers: {} with type: {}", handlers, type);
    }

    @Override
    public List<EventHandler> getHandler(String type) {
        return eventHandlers.get(type);
    }

    @Override
    public void deleteHandler(String type) {
        this.eventHandlers.remove(type);
    }
}
