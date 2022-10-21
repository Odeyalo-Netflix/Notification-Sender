package com.odeyalo.netflix.emailsenderservice.service.html.support;

import org.apache.velocity.app.VelocityEngine;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ExtendedModelMap;

import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class VelocityLibraryDynamicValuesHtmlTemplateInjectorTest {
    private final VelocityLibraryDynamicValuesHtmlTemplateInjector injector = new VelocityLibraryDynamicValuesHtmlTemplateInjector(new VelocityEngine());
    private static final String TEMPLATE_BODY = "Hello, ${username}";
    private static final String TEMPLATE_USERNAME_KEY = "username";
    private static final String TEMPLATE_USERNAME_VALUE = "Odeyalo";
    private static final String EXPECTED_TEMPLATE_RESULT = "Hello, Odeyalo";


    @Test
    void injectValues() {
        StringWriter writer = new StringWriter();
        ExtendedModelMap modelMap = new ExtendedModelMap();
        modelMap.addAttribute(TEMPLATE_USERNAME_KEY, TEMPLATE_USERNAME_VALUE);
        String result = injector.injectValuesFromTemplateBody(TEMPLATE_BODY, modelMap);
        assertNotNull(result);
        assertEquals(EXPECTED_TEMPLATE_RESULT, result);
    }
}
