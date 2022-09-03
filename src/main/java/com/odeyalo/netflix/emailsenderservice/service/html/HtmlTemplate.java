package com.odeyalo.netflix.emailsenderservice.service.html;

import com.odeyalo.netflix.emailsenderservice.service.html.support.HtmlTemplateRegistry;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public interface HtmlTemplate {

    String getHtmlTemplate() throws IOException;


    String getType();


    @Autowired
    default void registerMe(HtmlTemplateRegistry registry) {
        registry.registryTemplate(getType(), this);
    }
}
