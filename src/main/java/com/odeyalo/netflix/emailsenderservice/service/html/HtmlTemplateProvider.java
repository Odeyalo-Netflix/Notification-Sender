package com.odeyalo.netflix.emailsenderservice.service.html;

import com.odeyalo.netflix.emailsenderservice.service.html.support.HtmlTemplateRegistry;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * Returns raw html file data without dynamic values
 */
public interface HtmlTemplateProvider {

    String getHtmlTemplateBody() throws IOException;

    String getType();

    boolean containsDynamicValues();

    @Autowired
    default void autoRegisterInRegistry(HtmlTemplateRegistry registry) {
        registry.registryTemplate(getType(), this);
    }
}
