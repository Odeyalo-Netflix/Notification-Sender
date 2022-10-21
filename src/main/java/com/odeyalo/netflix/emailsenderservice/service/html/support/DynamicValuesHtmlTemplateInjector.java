package com.odeyalo.netflix.emailsenderservice.service.html.support;

import org.springframework.ui.Model;

/**
 * Inject dynamic values from model in html template
 */
public interface DynamicValuesHtmlTemplateInjector {
    /**
     *
     * @param path - path to template
     * @param model - model
     * @return - template with injected values
     */
    String injectValuesFromPath(String path, Model model);

    String injectValuesFromTemplateBody(String templateBody, Model model);
}
