package com.odeyalo.netflix.emailsenderservice.service.support.events;

import java.util.List;

public interface EventHandlerRegistry {

    void addEventHandler(String type, EventHandler handler);

    List<EventHandler> getHandler(String type);

    void deleteHandler(String type);
}
