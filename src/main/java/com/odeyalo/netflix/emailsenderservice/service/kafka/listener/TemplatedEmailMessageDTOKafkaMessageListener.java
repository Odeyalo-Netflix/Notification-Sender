package com.odeyalo.netflix.emailsenderservice.service.kafka.listener;

import com.odeyalo.netflix.emailsenderservice.service.html.HtmlTemplateFactory;
import com.odeyalo.netflix.emailsenderservice.service.sender.email.CachingEmailSender;
import com.odeyalo.netflix.emailsenderservice.service.support.MimeMessageBuilder;
import com.odeyalo.support.clients.notification.dto.TemplatedEmailMessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

@Component
public class TemplatedEmailMessageDTOKafkaMessageListener implements KafkaMessageListener<TemplatedEmailMessageDTO> {
    private final CachingEmailSender sender;
    private final HtmlTemplateFactory templateFactory;
    private final MimeMessageBuilder builder;

    @Autowired
    public TemplatedEmailMessageDTOKafkaMessageListener(CachingEmailSender sender, HtmlTemplateFactory templateFactory, MimeMessageBuilder builder) {
        this.sender = sender;
        this.templateFactory = templateFactory;
        this.builder = builder;
    }

    @Override
    @KafkaListener(topics = {"TEMPLATE_EMAIL_MESSAGE_TOPIC"})
    public void listen(TemplatedEmailMessageDTO templatedEmailMessageDTO) throws Exception {
        String templateType = templatedEmailMessageDTO.getTemplateType();
        String templateBody = this.templateFactory.getTemplateBody(templateType);
        MimeMessage message = this.builder.buildMimeMessage(templatedEmailMessageDTO, templateBody);
        this.sender.cacheMessage(message);
    }
}
