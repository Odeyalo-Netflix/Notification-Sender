package com.odeyalo.netflix.emailsenderservice.service.kafka.listener;

import com.odeyalo.netflix.emailsenderservice.dto.EmailMessageDTO;
import com.odeyalo.netflix.emailsenderservice.service.sender.email.EmailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public class EmailMessageDTOKafkaMessageListener implements KafkaMessageListener<EmailMessageDTO> {
    private final Logger logger = LoggerFactory.getLogger(EmailMessageDTOKafkaMessageListener.class);
    private final EmailSender manager;

    public EmailMessageDTOKafkaMessageListener(@Qualifier("googleXOauth2AsyncMailSender") EmailSender manager) {
        this.manager = manager;
    }

    @KafkaListener(topics = {"MAIL_MESSAGE_SENDER_TOPIC"},
            containerFactory = "emailMessageDTOConcurrentKafkaListenerContainerFactory")
    public void listen(EmailMessageDTO dto) throws MessagingException {
        this.logger.info("New message: to: {}, body: {}", dto.getTo(), dto.getBody());
        this.manager.send(dto.getBody(), dto.getSubject(), dto.getTo());
    }
}
