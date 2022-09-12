package com.odeyalo.netflix.emailsenderservice.service.html.support;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.spring.VelocityEngineUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.io.StringWriter;
import java.util.Map;

@Service
public class VelocityLibraryDynamicValuesHtmlTemplateInjector implements DynamicValuesHtmlTemplateInjector {
    private final VelocityEngine engine;

    @Autowired
    public VelocityLibraryDynamicValuesHtmlTemplateInjector(VelocityEngine engine) {
        this.engine = engine;
    }

    @Override
    public String injectValuesFromPath(String path, Model model) {
        return VelocityEngineUtils.mergeTemplateIntoString(engine, path, "UTF-8", model.asMap());
    }

    @Override
    public String injectValuesFromTemplateBody(String templateBody, Model model) {
        Map<String, Object> map = model.asMap();
        StringWriter writer = new StringWriter();
        Velocity.evaluate(new VelocityContext(map), writer, "", templateBody);
        return writer.toString();
    }
}
