package com.odeyalo.netflix.emailsenderservice.service.kafka.listener;

import com.odeyalo.netflix.emailsenderservice.service.sender.sms.PhoneNumberMessageSender;
import com.odeyalo.support.clients.dto.PhoneNumberSmsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PhoneNumberSmsDtoKafkaMessageListener implements KafkaMessageListener<PhoneNumberSmsDTO>{
    private final PhoneNumberMessageSender phoneNumberMessageSender;

    private final Logger logger = LoggerFactory.getLogger(PhoneNumberSmsDtoKafkaMessageListener.class);
    public PhoneNumberSmsDtoKafkaMessageListener(PhoneNumberMessageSender phoneNumberMessageSender) {
        this.phoneNumberMessageSender = phoneNumberMessageSender;
    }

    @Override
    @KafkaListener(topics = "PHONE_NUMBER_SMS_SENDER", containerFactory = "phoneNumberSmsDTOConcurrentKafkaListenerContainerFactory")
    public void listen(PhoneNumberSmsDTO phoneNumberSmsDTO) throws Exception {
        this.logger.info("Accept message: {}", phoneNumberSmsDTO);
        this.phoneNumberMessageSender.sendMessage(
                phoneNumberSmsDTO.getPhoneNumber(), phoneNumberSmsDTO.getBody()
        );
    }
}
