package com.odeyalo.netflix.emailsenderservice.service.html.support;

import com.odeyalo.netflix.emailsenderservice.service.html.HtmlTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class HtmlTemplateContainer implements HtmlTemplateRegistry {
    private final Map<String, HtmlTemplate> templates;

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
