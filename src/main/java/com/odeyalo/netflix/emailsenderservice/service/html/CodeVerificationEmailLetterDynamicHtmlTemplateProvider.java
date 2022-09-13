package com.odeyalo.netflix.emailsenderservice.service.html;

import com.odeyalo.netflix.emailsenderservice.service.html.support.CachedFileReader;
import com.odeyalo.netflix.emailsenderservice.service.html.support.DynamicValuesHtmlTemplateInjector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Contains the code verification template to send to user email
 */
@Component
public class CodeVerificationEmailLetterDynamicHtmlTemplateProvider implements DynamicHtmlTemplateProvider {
    private final String templateBody;
    private final DynamicValuesHtmlTemplateInjector injector;
    public static final String TEMPLATE_TYPE = "CODE_VERIFICATION_EMAIL_LETTER_TEMPLATE";

    @Autowired
    public CodeVerificationEmailLetterDynamicHtmlTemplateProvider(@Value("${app.templates.code.verification}") String path,
                                                                  DynamicValuesHtmlTemplateInjector injector,
                                                                  CachedFileReader reader) {
        this.templateBody = new String(reader.readAllBytes(Path.of(path)));
        this.injector = injector;
    }

    @Override
    public String getHtmlTemplate(Model model) throws IOException {
        return this.injector.injectValuesFromTemplateBody(templateBody, model);
    }

    @Override
    public String getType() {
        return TEMPLATE_TYPE;
    }
}
