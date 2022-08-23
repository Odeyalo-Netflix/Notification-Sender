package com.odeyalo.netflix.emailsenderservice.config.kafka;

import com.odeyalo.support.clients.dto.PhoneNumberSmsDTO;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
public class PhoneNumberDTOKafkaMessageBrokerConsumerConfiguration extends AbstractKafkaMessageBrokerConsumerConfigurationSupport {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PhoneNumberSmsDTO> phoneNumberSmsDTOConcurrentKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, PhoneNumberSmsDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(phoneNumberSmsDTOConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, PhoneNumberSmsDTO> phoneNumberSmsDTOConsumerFactory() {
        JsonDeserializer<PhoneNumberSmsDTO> deserializer = new JsonDeserializer<>(PhoneNumberSmsDTO.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);
        return new DefaultKafkaConsumerFactory<>(consumerConfig(), new StringDeserializer(), deserializer);
    }
}
