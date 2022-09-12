package com.odeyalo.netflix.emailsenderservice.service.kafka.listener;

import com.odeyalo.netflix.emailsenderservice.service.html.AdvancedHtmlTemplateFactory;
import com.odeyalo.netflix.emailsenderservice.service.html.DynamicHtmlTemplate;
import com.odeyalo.netflix.emailsenderservice.service.support.IncomingEmailMessageCachingService;
import com.odeyalo.netflix.emailsenderservice.service.support.MimeMessageBuilder;
import com.odeyalo.support.clients.notification.dto.DynamicTemplatedEmailMessageDTO;
import com.odeyalo.support.clients.notification.dto.TemplatedEmailMessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.ui.ExtendedModelMap;

import javax.mail.internet.MimeMessage;
import java.util.Map;

@Component
public class DynamicTemplatedEmailMessageDTOKafkaMessageListener implements KafkaMessageListener<DynamicTemplatedEmailMessageDTO> {
    private final IncomingEmailMessageCachingService cachingService;
    private final AdvancedHtmlTemplateFactory factory;
    private final MimeMessageBuilder builder;

    @Autowired
    public DynamicTemplatedEmailMessageDTOKafkaMessageListener(IncomingEmailMessageCachingService cachingService, AdvancedHtmlTemplateFactory factory, MimeMessageBuilder builder) {
        this.cachingService = cachingService;
        this.factory = factory;
        this.builder = builder;
    }

    @Override
    @KafkaListener(topics = "DYNAMIC_TEMPLATED_EMAIL_MESSAGE_TOPIC", containerFactory = "dynamicTemplatedEmailMessageDTOConcurrentKafkaListenerContainerFactory")
    public void listen(DynamicTemplatedEmailMessageDTO dto) throws Exception {
        Map<String, Object> modelMap = dto.getModel();
        String templateType = dto.getTemplateType();
        DynamicHtmlTemplate template = factory.getDynamicHtmlTemplate(templateType);
        ExtendedModelMap model = new ExtendedModelMap();
        model.addAllAttributes(modelMap);
        String body = template.getHtmlTemplate(model);
        MimeMessage message = this.builder.buildMimeMessage(new TemplatedEmailMessageDTO(dto.getTo(), dto.getSubject(), templateType), body);
        this.cachingService.cache(message);
    }
}
