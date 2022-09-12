package com.odeyalo.netflix.emailsenderservice.service.html;

public interface AdvancedHtmlTemplateFactory extends HtmlTemplateFactory {
    /**
     * Returns the HtmlTemplate without injected fields
     * @param type - template type
     * @return - DynamicHtmlTemplate without injected fields
     */
    DynamicHtmlTemplate getDynamicHtmlTemplate(String type);
}
