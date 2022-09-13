package com.odeyalo.netflix.emailsenderservice.service.html;

import org.springframework.ui.Model;

import java.io.IOException;

/**
 * Returns html template with dynamic values
 */
public interface DynamicHtmlTemplateProvider extends HtmlTemplateProvider {

    @Override
    default String getHtmlTemplateBody() throws IOException {
        throw new IllegalArgumentException("DynamicHtmlTemplate does not support raw html template. Use String getHtmlTemplate(Model model) method to avoid this exception");
    }

    String getHtmlTemplate(Model model) throws IOException;

    @Override
    default boolean containsDynamicValues() {
        return true;
    }
}
