package com.odeyalo.netflix.emailsenderservice.service.kafka.listener;

import com.odeyalo.netflix.emailsenderservice.service.html.HtmlTemplateProviderFactory;
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
    private final MimeMessageBuilder builder;
    private final HtmlTemplateProviderFactory templateFactory;

    @Autowired
    public TemplatedEmailMessageDTOKafkaMessageListener(CachingEmailSender sender,
                                                        MimeMessageBuilder builder, HtmlTemplateProviderFactory templateFactory) {
        this.sender = sender;
        this.builder = builder;
        this.templateFactory = templateFactory;
    }

    @Override
    @KafkaListener(topics = {"TEMPLATE_EMAIL_MESSAGE_TOPIC"}, containerFactory = "templatedEmailMessageDTOConcurrentKafkaListenerContainerFactory")
    public void listen(TemplatedEmailMessageDTO templatedEmailMessageDTO) throws Exception {
        String templateType = templatedEmailMessageDTO.getTemplateType();
        String templateBody = this.templateFactory.getHtmlTemplate(templateType).getHtmlTemplateBody();
        MimeMessage message = this.builder.buildMimeMessage(templatedEmailMessageDTO, templateBody);
        this.sender.cacheMessage(message);
    }
}
