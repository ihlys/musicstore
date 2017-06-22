package com.ihordev.core.util;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import io.github.lukehutch.fastclasspathscanner.scanner.ScanResult;

import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;


public final class NamedQueriesHelper {

    private NamedQueriesHelper() {
        throw new AssertionError("NamedQueriesHelper cannot be instantiated.");
    }

    /**
     * Searches for named queries in packages and classes. Returns all
     * {@link NamedQuery} annotations on classes that correspond to specified
     * package and name pattern. If class has {@link NamedQueries} annotation
     * then it takes {@code NamedQuery} annotations specified in it's value
     * property.
     *
     * @param basePackageToScan  the package where searching for {@code NamedQuery}
     *                           annotation must be taken.
     * @param namePattern  the pattern to which classes names must correspond.
     * @return set that contains found {@code NamedQuery} annotations
     */
    public static Set<NamedQuery> findNamedQueries(String basePackageToScan, String namePattern) {
        Pattern pattern = Pattern.compile(namePattern);

        FastClasspathScanner scanner = new FastClasspathScanner(basePackageToScan);
        ScanResult scanResult = scanner.scan();
        List<String> matchedClassesNames = scanResult.getNamesOfAllClasses().stream()
                .filter(className -> pattern.matcher(className).matches())
                .collect(toList());

        List<Class<?>> matchedClasses = scanResult.classNamesToClassRefs(matchedClassesNames);
        return getNamedQueryAnnotations(matchedClasses);
    }

    @SuppressWarnings("nullness")
    private static Set<NamedQuery> getNamedQueryAnnotations(List<Class<?>> matchedClasses) {
        Set<NamedQuery> namedQuerySet = new HashSet<>();
        for (Class<?> matchedClass : matchedClasses) {
            if (matchedClass.isAnnotationPresent(NamedQuery.class)) {
                namedQuerySet.add(matchedClass.getAnnotation(NamedQuery.class));
            } else if (matchedClass.isAnnotationPresent(NamedQueries.class)) {
                namedQuerySet.addAll(asList(matchedClass.getAnnotation(NamedQueries.class).value()));
            }
        }

        return namedQuerySet;
    }

    /**
     * Searches for native named queries in packages and classes. Returns all
     * {@link NamedNativeQuery} annotations on classes that correspond to specified
     * package and name pattern. If class has {@link NamedNativeQueries} annotation
     * then it takes {@code NamedNativeQuery} annotations specified in it's value
     * property.
     *
     * @param basePackageToScan  the package where searching for {@code NamedNativeQuery}
     *                           annotation must be taken.
     * @param namePattern  the pattern to which classes names must correspond.
     * @return set that contains found {@code NamedNativeQuery} annotations
     */
    public static Set<NamedNativeQuery> findNamedNativeQueries(String basePackageToScan, String namePattern) {
        Pattern pattern = Pattern.compile(namePattern);

        FastClasspathScanner scanner = new FastClasspathScanner(basePackageToScan);
        ScanResult scanResult = scanner.scan();
        List<String> matchedClassesNames = scanResult.getNamesOfAllClasses().stream()
                .filter(className -> pattern.matcher(className).matches())
                .collect(toList());

        List<Class<?>> matchedClasses = scanResult.classNamesToClassRefs(matchedClassesNames);
        return getNamedNativeQueryAnnotations(matchedClasses);
    }

    @SuppressWarnings("nullness")
    private static Set<NamedNativeQuery> getNamedNativeQueryAnnotations(List<Class<?>> matchedClasses) {
        Set<NamedNativeQuery> namedQuerySet = new HashSet<>();
        for (Class<?> matchedClass : matchedClasses) {
            if (matchedClass.isAnnotationPresent(NamedNativeQuery.class)) {
                namedQuerySet.add(matchedClass.getAnnotation(NamedNativeQuery.class));
            } else if (matchedClass.isAnnotationPresent(NamedNativeQueries.class)) {
                namedQuerySet.addAll(asList(matchedClass.getAnnotation(NamedNativeQueries.class).value()));
            }
        }
        return namedQuerySet;
    }

}
