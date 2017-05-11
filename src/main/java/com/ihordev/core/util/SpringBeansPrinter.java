package com.ihordev.core.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;


public interface SpringBeansPrinter {

    @Autowired
    default void print(ApplicationContext ctx) {
        System.out.println("Current beans loaded in Spring Boot Container: ");

		String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
    }
}
