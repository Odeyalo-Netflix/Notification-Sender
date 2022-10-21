package com.odeyalo.netflix.emailsenderservice.service.html;

public interface AdvancedHtmlTemplateProviderFactory extends HtmlTemplateProviderFactory {
    /**
     * Returns the HtmlTemplate without injected fields
     * @param type - template type
     * @return - DynamicHtmlTemplate without injected fields
     */
    DynamicHtmlTemplateProvider getDynamicHtmlTemplate(String type);
}
