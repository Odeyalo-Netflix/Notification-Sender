package com.odeyalo.netflix.emailsenderservice.service.kafka.listener;

import com.odeyalo.netflix.emailsenderservice.service.support.MimeMessageBuilder;
import com.odeyalo.support.clients.notification.dto.EmailMessageDTO;
import com.odeyalo.netflix.emailsenderservice.service.sender.email.CachingEmailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailMessageDTOKafkaMessageListener implements KafkaMessageListener<EmailMessageDTO> {
    private final Logger logger = LoggerFactory.getLogger(EmailMessageDTOKafkaMessageListener.class);
    private final CachingEmailSender emailSender;
    private final MimeMessageBuilder builder;


    @Autowired
    public EmailMessageDTOKafkaMessageListener(@Qualifier("googleXOauth2AsyncCachingMailSender") CachingEmailSender emailSender, MimeMessageBuilder builder) {
        this.emailSender = emailSender;
        this.builder = builder;
    }

    @KafkaListener(topics = {"MAIL_MESSAGE_SENDER_TOPIC"},
            containerFactory = "emailMessageDTOConcurrentKafkaListenerContainerFactory")
    public void listen(EmailMessageDTO dto) throws MessagingException {
        this.logger.info("New message: to: {}, body: {}", dto.getTo(), dto.getBody());
        MimeMessage message = this.builder.buildMimeMessage(dto);
        this.emailSender.cacheMessage(message);
    }
}
