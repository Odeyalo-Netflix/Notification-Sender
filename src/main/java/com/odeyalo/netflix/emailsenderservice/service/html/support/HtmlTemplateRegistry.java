package com.odeyalo.netflix.emailsenderservice.service.html.support;

import com.odeyalo.netflix.emailsenderservice.service.html.HtmlTemplate;

public interface HtmlTemplateRegistry {
    /**
     * Returns the specific html template
     * @param type - template type
     * @return - html template
     */
    HtmlTemplate getTemplate(String type);

    void registryTemplate(String type, HtmlTemplate template);

    boolean containsTemplate(String type);

    void removeTemplate(String type);

}
