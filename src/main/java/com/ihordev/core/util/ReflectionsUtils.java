package com.ihordev.core.util;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Stream;

import static java.lang.Character.toLowerCase;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;


public final class ReflectionsUtils {

    private ReflectionsUtils() {
        throw new AssertionError("ReflectionsUtils cannot be instantiated.");
    }


    public static List<Method> getPropertyGettersMethodsFromClass(Class<?> targetClass) {
        return Stream.of(targetClass.getDeclaredMethods())
            .filter(ReflectionsUtils::isPropertyGetter)
            .collect(toList());
    }

    public static String getPropertyName(Method method) {
        String methodName = method.getName();
        if (isPropertyGetter(method)) {
            return toLowerCase(methodName.charAt(3)) + methodName.substring(4, methodName.length());
        } else {
            String errMsg = format("Cannot get property name from method with name %s," +
                    " method is not property getter.", methodName);
            throw new IllegalArgumentException(errMsg);
        }
    }

    public static List<String> getPropertiesNames(Class<?> targetClass) {
        return getPropertyGettersMethodsFromClass(targetClass).stream()
                .map(ReflectionsUtils::getPropertyName)
                .collect(toList());
    }

    public static boolean isPropertyGetter(Method method) {
        String methodName = method.getName();
        return methodName.startsWith("get")
                && methodName.length() > 3
                && method.getParameterTypes().length == 0;
    }
}
