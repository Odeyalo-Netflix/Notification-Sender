package com.odeyalo.netflix.emailsenderservice.service.html;

import com.odeyalo.netflix.emailsenderservice.service.html.support.HtmlTemplateRegistry;
import org.springframework.stereotype.Component;

@Component
public class DefaultAdvancedHtmlTemplateProviderFactory implements AdvancedHtmlTemplateProviderFactory {
    private final HtmlTemplateRegistry container;

    public DefaultAdvancedHtmlTemplateProviderFactory(HtmlTemplateRegistry container) {
        this.container = container;
    }

    @Override
    public HtmlTemplateProvider getHtmlTemplate(String type) {
        HtmlTemplateProvider template = container.getTemplate(type);
        if (template == null) {
            throw new IllegalArgumentException(String.format("The factory cannot create or return the HtmlTemplate with type: %s", type));
        }
        return template;
    }


    @Override
    public DynamicHtmlTemplateProvider getDynamicHtmlTemplate(String type) {
        HtmlTemplateProvider template = container.getTemplate(type);
        if (!(template instanceof DynamicHtmlTemplateProvider)) {
            throw new IllegalArgumentException(String.format("The html template with type: %s is null or HtmlTemplate isn't DynamicHtmlTemplate instance." +
                    "HtmlTemplate info: %s", type, template));
        }
        return (DynamicHtmlTemplateProvider) template;
    }
}
