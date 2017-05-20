package com.ihordev.core.util;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Stream;

import static java.lang.Character.toLowerCase;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;


public final class ReflectionsUtils {

    private ReflectionsUtils() {
        throw new AssertionError("ReflectionsUtils cannot be instantiated.");
    }

    public static boolean isClassOverridesMethod(Class<?> targetClass, String method, Class<?>... parameterTypes) {
        try {
            return targetClass.getMethod(method, parameterTypes).getDeclaringClass() == targetClass;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Method> getGetterMethodsFromClass(Class<?> targetClass) {
        return Stream.of(targetClass.getDeclaredMethods())
            .filter(method -> method.getName().startsWith("get"))
            .collect(toList());
    }

    public static String getPropertyName(Method method) {
        String methodName = method.getName();
        if (methodName.startsWith("get") && methodName.length() > 3) {
            return toLowerCase(methodName.charAt(3)) + methodName.substring(4, methodName.length());
        } else {
            String errMsg = format("Cannot get property name from method with name %s," +
                    " method is not property getter.", methodName);
            throw new IllegalArgumentException(errMsg);
        }
    }
}
