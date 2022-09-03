package com.odeyalo.netflix.emailsenderservice.service.html;

import com.odeyalo.netflix.emailsenderservice.service.html.support.HtmlTemplateRegistry;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * HtmlTemplateFactory implementation that cached templates to boost performance
 */

@Service
public class CachingHtmlTemplateFactory implements HtmlTemplateFactory {
    private final Map<String, String> cache = new ConcurrentHashMap<>();
    private final HtmlTemplateRegistry registry;

    public CachingHtmlTemplateFactory(HtmlTemplateRegistry registry) {
        this.registry = registry;
    }

    @Override
    public String getTemplateBody(String type) {
        String cachedTemplate = cache.get(type);
        if (cachedTemplate != null) {
            return cachedTemplate;
        }
        try {
            HtmlTemplate template = registry.getTemplate(type);
            String htmlTemplateBody = template.getHtmlTemplate();
            this.cache.put(type, htmlTemplateBody);
            return htmlTemplateBody;
        } catch (Exception ex) {
            throw new IllegalArgumentException(String.format("The factory cannot create or return the cached object with type: %s, since it doesn't exist", type));
        }
    }
}
