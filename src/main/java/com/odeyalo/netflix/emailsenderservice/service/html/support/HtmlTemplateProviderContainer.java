package com.odeyalo.netflix.emailsenderservice.service.html.support;

import com.odeyalo.netflix.emailsenderservice.service.html.HtmlTemplateProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class HtmlTemplateProviderContainer implements HtmlTemplateProviderRegistry {
    private final Map<String, HtmlTemplateProvider> templates;
    private final Logger logger = LoggerFactory.getLogger(HtmlTemplateProviderContainer.class);

    public HtmlTemplateProviderContainer() {
        this.templates = new HashMap<>();
    }

    @Override
    public HtmlTemplateProvider getTemplate(String type) {
        return templates.get(type);
    }

    @Override
    public void registryTemplate(String type, HtmlTemplateProvider template) {
        this.templates.put(type, template);
        this.logger.info("Registered: {}, {}", type, template);
    }

    @Override
    public boolean containsTemplate(String type) {
        return templates.containsKey(type);
    }

    @Override
    public void removeTemplate(String type) {
        this.templates.remove(type);
    }
}
