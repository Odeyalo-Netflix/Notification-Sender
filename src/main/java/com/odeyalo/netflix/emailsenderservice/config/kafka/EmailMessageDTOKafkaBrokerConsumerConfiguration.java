package com.odeyalo.netflix.emailsenderservice.config.kafka;

import com.odeyalo.netflix.emailsenderservice.dto.EmailMessageDTO;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
public class EmailMessageDTOKafkaBrokerConsumerConfiguration extends AbstractKafkaMessageBrokerConsumerConfigurationSupport {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, EmailMessageDTO> emailMessageDTOConcurrentKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, EmailMessageDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(emailMessageDTOConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, EmailMessageDTO> emailMessageDTOConsumerFactory() {
        JsonDeserializer<EmailMessageDTO> deserializer = new JsonDeserializer<>(EmailMessageDTO.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);
        return new DefaultKafkaConsumerFactory<>(consumerConfig(), new StringDeserializer(), deserializer);
    }
}
