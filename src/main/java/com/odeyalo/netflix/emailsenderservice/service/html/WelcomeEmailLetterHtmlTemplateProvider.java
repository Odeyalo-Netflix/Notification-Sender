package com.odeyalo.netflix.emailsenderservice.service.html;

import com.odeyalo.netflix.emailsenderservice.service.html.support.CachedFileReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class WelcomeEmailLetterHtmlTemplateProvider implements HtmlTemplateProvider {
    private final String path;
    public static final String TEMPLATE_TYPE = "WELCOME_EMAIL_LETTER_TEMPLATE";
    private final CachedFileReader reader;

    @Autowired
    public WelcomeEmailLetterHtmlTemplateProvider(@Value("${app.templates.letter.welcome-on-register}") String path, CachedFileReader reader) {
        if (Files.notExists(Path.of(path))) {
            throw new IllegalArgumentException(String.format("The application cannot find the html template with path: %s", path));
        }
        this.path = path;
        this.reader = reader;
    }

    @Override
    public String getHtmlTemplateBody() {
        byte[] bytes = reader.readAllBytes(Path.of(path));
        return new String(bytes);
    }

    @Override
    public String getType() {
        return TEMPLATE_TYPE;
    }

    @Override
    public boolean containsDynamicValues() {
        return false;
    }
}
