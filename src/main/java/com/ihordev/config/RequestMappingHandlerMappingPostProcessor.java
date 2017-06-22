package com.ihordev.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

public class RequestMappingHandlerMappingPostProcessor implements BeanPostProcessor {

    private static final Logger logger = LoggerFactory.getLogger(RequestMappingHandlerMappingPostProcessor.class);

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {
        if (bean instanceof RequestMappingHandlerMapping) {
            setUseSuffixPatternMatch((RequestMappingHandlerMapping) bean, beanName);
        }
        return bean;
    }

    private void setUseSuffixPatternMatch(RequestMappingHandlerMapping requestMappingHandlerMapping,
                                          String beanName) {
        logger.info("Setting 'UseSuffixPatternMatch' on 'RequestMappingHandlerMapping'-bean to false." +
                " Bean name: {}", beanName);
        requestMappingHandlerMapping.setUseSuffixPatternMatch(false);
    }
}
