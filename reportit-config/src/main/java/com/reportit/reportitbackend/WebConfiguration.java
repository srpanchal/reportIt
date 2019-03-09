package com.reportit.reportitbackend;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/docs/**").addResourceLocations("classpath:/META-INF/resources/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/docs/v2/api-docs", "/v2/api-docs");
        registry.addRedirectViewController("/docs/configuration/ui", "/configuration/ui");
        registry.addRedirectViewController("/docs/configuration/security", "/configuration/security");
        registry.addRedirectViewController("/docs/swagger-resources", "/swagger-resources");
        registry.addRedirectViewController("/docs", "/swagger-ui.html");
        // registry.addViewController("/docs").setViewName("/swagger-ui.html");
    }

}

