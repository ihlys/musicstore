package com.ihordev.core.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.stream.Stream;


/**
 * An interface that can be implemented by any Spring Bean to automatically
 * print to {@code System.out} all beans that are loaded in Spring Boot
 * container while been registered.
 */
public interface SpringBeansPrinter {

    @Autowired
    default void print(ApplicationContext ctx) {
        System.out.println("Current beans loaded in Spring Boot Container: ");

        Stream.of(ctx.getBeanDefinitionNames())
                .sorted()
                .forEach(System.out::println);
    }
}
