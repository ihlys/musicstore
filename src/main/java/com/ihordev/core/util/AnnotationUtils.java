package com.ihordev.core.util;

import com.ihordev.core.navigation.WithNavigationLinks;
import org.springframework.web.method.HandlerMethod;

import javax.persistence.Entity;
import java.lang.reflect.Method;


public final class AnnotationUtils {

    private AnnotationUtils() {
        throw new AssertionError("AnnotationUtils cannot be instantiated.");
    }


    // TODO: looks like very specific method, should it be private in the class that uses it?
    public static boolean isWithNavigationLinks(HandlerMethod target) {
        Method method = target.getMethod();
        if (method.getDeclaredAnnotation(WithNavigationLinks.class) != null) {
            return true;
        } else {
            return method.getDeclaringClass().getDeclaredAnnotation(WithNavigationLinks.class) != null;
        }
    }

    public static boolean isJPAEntity(Class<?> cls) {
        return cls.isAnnotationPresent(Entity.class);
    }
}
