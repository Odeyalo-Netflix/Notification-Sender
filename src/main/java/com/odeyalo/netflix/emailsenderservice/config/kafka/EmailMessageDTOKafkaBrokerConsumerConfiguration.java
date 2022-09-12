package com.odeyalo.netflix.emailsenderservice.config.kafka;

import com.odeyalo.support.clients.notification.dto.DynamicTemplatedEmailMessageDTO;
import com.odeyalo.support.clients.notification.dto.EmailMessageDTO;
import com.odeyalo.support.clients.notification.dto.TemplatedEmailMessageDTO;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
public class EmailMessageDTOKafkaBrokerConsumerConfiguration extends AbstractKafkaMessageBrokerConsumerConfigurationSupport {

    private final static long DEFAULT_INTERVAL = 10000;
    private final static long DEFAULT_MAX_ATTEMPTS = 5;
    private final Logger logger = LoggerFactory.getLogger(EmailMessageDTOKafkaBrokerConsumerConfiguration.class);

    @Bean
    public StringJsonMessageConverter converter() {
        return new StringJsonMessageConverter();
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, EmailMessageDTO> emailMessageDTOConcurrentKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, EmailMessageDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(emailMessageDTOConsumerFactory());
        factory.setCommonErrorHandler(new DefaultErrorHandler(new FixedBackOff(DEFAULT_INTERVAL, DEFAULT_MAX_ATTEMPTS)));
        return factory;
    }


    @Bean
    public ConsumerFactory<String, EmailMessageDTO> emailMessageDTOConsumerFactory() {
        JsonDeserializer<EmailMessageDTO> deserializer = new JsonDeserializer<>(EmailMessageDTO.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);
        return new DefaultKafkaConsumerFactory<>(consumerConfig(), new ErrorHandlingDeserializer<>(new StringDeserializer()), new ErrorHandlingDeserializer<>(deserializer));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, TemplatedEmailMessageDTO> templatedEmailMessageDTOConcurrentKafkaListenerContainerFactory(RetryTemplate retryTemplate) {
        ConcurrentKafkaListenerContainerFactory<String, TemplatedEmailMessageDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(templatedEmailMessageDTOConsumerFactory());
        Handler handler = new Handler();
        factory.setCommonErrorHandler(handler);
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, DynamicTemplatedEmailMessageDTO> dynamicTemplatedEmailMessageDTOConcurrentKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, DynamicTemplatedEmailMessageDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(dynamicTemplatedEmailMessageDTOConsumerFactory());
//        Handler handler = new Handler();
//        factory.setCommonErrorHandler(handler);
        return factory;
    }


    @Bean
    public ConsumerFactory<String, DynamicTemplatedEmailMessageDTO> dynamicTemplatedEmailMessageDTOConsumerFactory() {
        JsonDeserializer<DynamicTemplatedEmailMessageDTO> deserializer = new JsonDeserializer<>(DynamicTemplatedEmailMessageDTO.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);
        return new DefaultKafkaConsumerFactory<>(consumerConfig(), new StringDeserializer(), deserializer);
    }
    @Bean
    public ConsumerFactory<String, TemplatedEmailMessageDTO> templatedEmailMessageDTOConsumerFactory() {
        JsonDeserializer<TemplatedEmailMessageDTO> deserializer = new JsonDeserializer<>(TemplatedEmailMessageDTO.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);
        ErrorHandlingDeserializer<String> keyErrorHandlingDeserializer = new ErrorHandlingDeserializer<>(new StringDeserializer());
        keyErrorHandlingDeserializer.setFailedDeserializationFunction((function) -> {
            if (function == null) {
                this.logger.warn("The FailedDeserializationFunction is null. Exception handling skipped.");
                return "Null";
            }
            Exception exception = function.getException();
            Headers headers = function.getHeaders();
            byte[] data = function.getData();
            this.logger.error("Failed to deserialize object with payload: {}", function);
            return "Failed to process";
        });
        ErrorHandlingDeserializer<TemplatedEmailMessageDTO> valueEmailMessageDTOErrorHandlingDeserializer = new ErrorHandlingDeserializer<>(deserializer);
        valueEmailMessageDTOErrorHandlingDeserializer.setFailedDeserializationFunction((function) -> {
            if (function == null) {
                this.logger.warn("The FailedDeserializationFunction is null. Exception handling skipped.");
                return new TemplatedEmailMessageDTO("", "", "");
            }
            Exception exception = function.getException();
            Headers headers = function.getHeaders();
            byte[] data = function.getData();
            this.logger.error("Failed to deserialize object with payload: {}", function);
            return new TemplatedEmailMessageDTO("", "", "");
        });
        return new DefaultKafkaConsumerFactory<>(consumerConfig(), keyErrorHandlingDeserializer, valueEmailMessageDTOErrorHandlingDeserializer);
    }
    class Handler extends DefaultErrorHandler {
        @Override
        public void handleRecord(Exception thrownException, ConsumerRecord<?, ?> record, Consumer<?, ?> consumer, MessageListenerContainer container) {
            logger.info("Error was occurred");
        }
    }
}

