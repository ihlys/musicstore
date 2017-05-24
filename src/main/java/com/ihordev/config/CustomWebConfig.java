package com.ihordev.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
@EnableSpringDataWebSupport
public class CustomWebConfig extends WebMvcConfigurerAdapter {

    @Autowired(required = false)
    private HandlerInterceptor navigationAddingHandlerInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (navigationAddingHandlerInterceptor != null) {
            registry.addInterceptor(navigationAddingHandlerInterceptor);
        }
    }

}
