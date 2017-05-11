package com.ihordev.web.config;

import org.springframework.boot.test.context.TestComponent;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.Locale;


@TestComponent
public class StubViewResolver implements ViewResolver, Ordered {


    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
        return new StubView();
    }

    @Override
    public int getOrder() {
        return 0;
    }

}
