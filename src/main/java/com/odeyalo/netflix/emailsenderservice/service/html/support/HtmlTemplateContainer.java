package com.odeyalo.netflix.emailsenderservice.service.html.support;

import com.odeyalo.netflix.emailsenderservice.service.html.HtmlTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class HtmlTemplateContainer implements HtmlTemplateRegistry {
    private final Map<String, HtmlTemplate> templates;
    private final Logger logger = LoggerFactory.getLogger(HtmlTemplateContainer.class);

    public HtmlTemplateContainer() {
        this.templates = new HashMap<>();
    }

    @Override
    public HtmlTemplate getTemplate(String type) {
        return templates.get(type);
    }

    @Override
    public void registryTemplate(String type, HtmlTemplate template) {
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
