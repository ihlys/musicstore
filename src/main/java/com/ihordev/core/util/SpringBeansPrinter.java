package com.ihordev.core.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;
import java.util.stream.Stream;


public interface SpringBeansPrinter {

    @Autowired
    default void print(ApplicationContext ctx) {
        System.out.println("Current beans loaded in Spring Boot Container: ");

        Stream.of(ctx.getBeanDefinitionNames())
                .sorted()
                .forEach(System.out::println);
    }
}
