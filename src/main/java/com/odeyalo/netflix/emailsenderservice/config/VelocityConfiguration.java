package com.odeyalo.netflix.emailsenderservice.config;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.spring.VelocityEngineFactory;
import org.apache.velocity.spring.VelocityEngineFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class VelocityConfiguration {

    @Bean
    public VelocityEngineFactory velocityEngineFactory() {
        VelocityEngineFactoryBean bean = new VelocityEngineFactoryBean();
        Properties properties = new Properties();
        properties.setProperty("resource.default_encoding", "UTF-8");
        properties.setProperty("output.encoding", "UTF-8");
        properties.setProperty("resource.loaders", "class, file");
        properties.setProperty("resource.loader.class.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        bean.setVelocityProperties(properties);
        return bean;
    }

    @Bean
    public VelocityEngine velocityEngine() throws Exception {
        Properties properties = new Properties();
        properties.setProperty("resource.default_encoding", "UTF-8");
        properties.setProperty("output.encoding", "UTF-8");
        properties.setProperty("resource.loaders", "class, file");
        properties.setProperty("resource.loader.class.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        VelocityEngine velocityEngine = new VelocityEngine(properties);
        velocityEngine.init();
        return velocityEngine;
    }

}
