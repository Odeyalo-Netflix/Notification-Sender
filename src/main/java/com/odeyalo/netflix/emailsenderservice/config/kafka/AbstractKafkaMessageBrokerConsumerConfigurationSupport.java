package com.odeyalo.netflix.emailsenderservice.config.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;

public abstract class AbstractKafkaMessageBrokerConsumerConfigurationSupport {
    protected static final String APACHE_KAFKA_MESSAGE_BROKER_CONNECTION_URL = "localhost:9092";

    protected HashMap<String, Object> consumerConfig() {
        HashMap<String, Object> config = new HashMap<>(5);
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, APACHE_KAFKA_MESSAGE_BROKER_CONNECTION_URL);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "1");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return config;
    }
}
