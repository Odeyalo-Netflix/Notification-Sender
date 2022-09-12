package com.odeyalo.netflix.emailsenderservice.service.html;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class PremiumMemberHtmlTemplate implements HtmlTemplate {
    public static final String TEMPLATE_TYPE_NAME = "PREMIUM_MEMBER_EMAIL_LETTER";
    private final String path = "C:\\Users\\thepr_2iz2cnv\\IdeaProjects\\emailsenderservice\\src\\main\\resources\\templates\\premium.html"; //todo
    private String cache;

    @Override
    public String getHtmlTemplateBody() throws IOException {
        if (cache != null) {
            return cache;
        }
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        String body = new String(bytes);
        this.cache = body;
        return body;
    }

    @Override
    public String getType() {
        return TEMPLATE_TYPE_NAME;
    }

    @Override
    public boolean containsDynamicValues() {
        return false;
    }
}
