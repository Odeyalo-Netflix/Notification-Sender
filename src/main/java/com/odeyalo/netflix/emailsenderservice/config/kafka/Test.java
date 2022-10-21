package com.odeyalo.netflix.emailsenderservice.config.kafka;

import com.odeyalo.support.clients.notification.dto.DynamicTemplatedEmailMessageDTO;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;

@Configuration
public class Test {
    @Bean
    public KafkaTemplate<String, DynamicTemplatedEmailMessageDTO> template(ProducerFactory<String, DynamicTemplatedEmailMessageDTO> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public ProducerFactory<String, DynamicTemplatedEmailMessageDTO> producerFactory() {
        return new DefaultKafkaProducerFactory<>(config(), new StringSerializer(), new JsonSerializer<>());
    }

    HashMap<String, Object> config() {
        HashMap<String, Object> config = new HashMap<>(5);
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return config;
    }
}
