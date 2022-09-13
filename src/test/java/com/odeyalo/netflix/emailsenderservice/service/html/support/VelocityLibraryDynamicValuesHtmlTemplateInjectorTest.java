package com.odeyalo.netflix.emailsenderservice.service.html.support;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import java.io.StringWriter;
import java.nio.file.Paths;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
//@SpringBootTest
//@TestPropertySource(value = "classpath:application.properties")
class VelocityLibraryDynamicValuesHtmlTemplateInjectorTest {
    @Autowired
    private VelocityLibraryDynamicValuesHtmlTemplateInjector injector;

    @Test
    void injectValues() {
//        ExtendedModelMap map = new ExtendedModelMap();
//        map.addAttribute("username", "aboba");
//        String s =
//                injector.injectValues(
//                        ("src\\main\\resources\\templates\\codeverification.html"),
//                map);
//        System.out.println(s);
        StringWriter writer = new StringWriter();
        boolean evaluate = Velocity.evaluate(new VelocityContext(Collections.singletonMap("username", "aboba")), writer, "", "Hello, ${username}");
        System.out.println(evaluate);
        System.out.println(writer.toString());
    }
}
