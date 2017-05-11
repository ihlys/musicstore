package com.ihordev.core.util;

import com.ihordev.core.navigation.WithNavigationLinks;
import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.Method;


public class AnnotationUtils {

    public static boolean isWithNavigationLinks(HandlerMethod target) {
        Method method = target.getMethod();
        if (method.getDeclaredAnnotation(WithNavigationLinks.class) != null) {
            return true;
        } else {
            return method.getDeclaringClass().getDeclaredAnnotation(WithNavigationLinks.class) != null;
        }
    }
}
