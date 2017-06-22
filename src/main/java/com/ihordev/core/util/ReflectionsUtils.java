package com.ihordev.core.util;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Stream;

import static java.lang.Character.toLowerCase;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toSet;


public final class ReflectionsUtils {

    private ReflectionsUtils() {
        throw new AssertionError("ReflectionsUtils cannot be instantiated.");
    }

    private static Set<Method> classMethods = new HashSet<>();
    static {
        classMethods.addAll(asList(Class.class.getMethods()));
    }

    /**
     * Gets methods from specified class that are getters for properties.
     * Performs search also in all super classes, super interfaces, but
     * excludes all methods declared in {@link Class}.
     *
     * @param targetClass  the class where property getters must be found
     * @return an unordered set that contains all found property getters
     *         in class.
     */
    public static Set<Method> getPropertyGettersMethodsFromClass(Class<?> targetClass) {
        return getPropertyGettersMethodsFromClassStream(targetClass)
            .collect(toSet());
    }

    /**
     * Gets methods from specified class that are getters for properties
     * and perform sorting with specified {@code Comparator} instance.
     * Performs search also in all super classes, super interfaces, but
     * excludes all methods declared in {@link Class}.
     *
     * @param targetClass  the class where property getters must be found
     * @param comparator  the comparator to sort returned property getters
     * @return an order set that contains all found property getters
     *         in class.
     */
    public static SortedSet<Method> getPropertyGettersMethodsFromClass(Class<?> targetClass, Comparator<Method> comparator) {
        return getPropertyGettersMethodsFromClassStream(targetClass)
            .collect(toCollection(() -> new TreeSet<>(comparator)));
    }

    private static Stream<Method> getPropertyGettersMethodsFromClassStream(Class<?> targetClass) {
        return Stream.of(targetClass.getMethods())
            .filter(method -> !classMethods.contains(method))
            .filter(ReflectionsUtils::isPropertyGetter);
    }

    /**
     * Returns property name from specified method. Assumes that method
     * is a property getter and if it is not, throws
     * {@code IllegalArgumentException}.
     *
     * @param method  the property getter method from which property
     *                name must be obtained
     * @return property name of property getter method
     *
     * @throws IllegalArgumentException if specified method is not property
     *         getter
     *
     * @see #isPropertyGetter(Method)
     */
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

    /**
     * Gets properties names for specified class. Performs search also in all
     * super classes, super interfaces, but excludes all methods declared in
     * {@link Class}.
     *
     * @param targetClass  the class where properties names must be found
     * @return an unordered set that contains all properties names in class
     */
    public static Set<String> getPropertiesNames(Class<?> targetClass) {
        return getPropertyGettersMethodsFromClass(targetClass).stream()
                .map(ReflectionsUtils::getPropertyName)
                .collect(toSet());
    }

    /**
     * Gets properties names for specified class and perform sorting with
     * specified {@code Comparator} instance. Performs search also in all
     * super classes, super interfaces, but excludes all methods declared in
     * {@link Class}.
     *
     * @param targetClass  the class where properties names must be found
     * @param comparator  the comparator to sort returned properties names
     * @return an unordered set that contains all properties names in class
     */
    public static SortedSet<String> getPropertiesNames(Class<?> targetClass, Comparator<String> comparator) {
        return getPropertyGettersMethodsFromClass(targetClass).stream()
                .map(ReflectionsUtils::getPropertyName)
                .collect(toCollection(() -> new TreeSet<>(comparator)));
    }

    /**
     * Checks if specified {@code Method} instance is a property getter,
     * that is a method that doesn't have parameters, starts with "get"
     * substring and have name with length greater that 3 (property name
     * is not empty).
     *
     * @param method  the method to check if it is property getter
     * @return true if specified method is property getter
     */
    public static boolean isPropertyGetter(Method method) {
        String methodName = method.getName();
        return methodName.startsWith("get")
                && methodName.length() > 3
                && method.getParameterTypes().length == 0;
    }

}
