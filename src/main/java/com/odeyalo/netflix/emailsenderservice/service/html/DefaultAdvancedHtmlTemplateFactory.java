package com.odeyalo.netflix.emailsenderservice.service.html;

import com.odeyalo.netflix.emailsenderservice.service.html.support.HtmlTemplateRegistry;
import org.springframework.stereotype.Component;

@Component
public class DefaultAdvancedHtmlTemplateFactory implements AdvancedHtmlTemplateFactory {
    private final HtmlTemplateRegistry container;

    public DefaultAdvancedHtmlTemplateFactory(HtmlTemplateRegistry container) {
        this.container = container;
    }

    @Override
    public HtmlTemplate getHtmlTemplate(String type) {
        HtmlTemplate template = container.getTemplate(type);
        if (template == null) {
            throw new IllegalArgumentException(String.format("The factory cannot create or return the HtmlTemplate with type: %s", type));
        }
        return template;
    }


    @Override
    public DynamicHtmlTemplate getDynamicHtmlTemplate(String type) {
        HtmlTemplate template = container.getTemplate(type);
        if (!(template instanceof DynamicHtmlTemplate)) {
            throw new IllegalArgumentException(String.format("The html template with type: %s is null or HtmlTemplate isn't DynamicHtmlTemplate instance." +
                    "HtmlTemplate info: %s", type, template));
        }
        return (DynamicHtmlTemplate) template;
    }
}
