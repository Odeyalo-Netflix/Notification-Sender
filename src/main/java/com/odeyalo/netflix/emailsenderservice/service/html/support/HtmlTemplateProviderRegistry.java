package com.odeyalo.netflix.emailsenderservice.service.html.support;

import com.odeyalo.netflix.emailsenderservice.service.html.HtmlTemplateProvider;

public interface HtmlTemplateProviderRegistry {
    /**
     * Returns the specific html template
     * @param type - template type
     * @return - html template
     */
    HtmlTemplateProvider getTemplate(String type);

    void registryTemplate(String type, HtmlTemplateProvider template);

    boolean containsTemplate(String type);

    void removeTemplate(String type);

}
