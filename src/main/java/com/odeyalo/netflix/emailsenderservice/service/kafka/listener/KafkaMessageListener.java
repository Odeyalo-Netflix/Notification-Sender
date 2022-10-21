package com.odeyalo.netflix.emailsenderservice.service.kafka.listener;

public interface KafkaMessageListener<T> {

    void listen(T t) throws Exception;

}
