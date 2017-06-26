package com.mark.nevexandrunkeeper.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Mark Cunningham on 6/25/2017.
 */
@Configuration
public class ApplicationConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String resourcePattern = "/resources/**";
        if ( !registry.hasMappingForPattern(resourcePattern)) {
            registry.addResourceHandler(resourcePattern).addResourceLocations("/resources/");
        }
    }
}
