package com.ihordev.core.jpa.namedqueries;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import io.github.lukehutch.fastclasspathscanner.scanner.ScanResult;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;


public final class NamedQueriesHelper {

    private NamedQueriesHelper() {
        throw new AssertionError("NamedQueriesHelper cannot be instantiated.");
    }

    public static Set<NamedQuery> findNamedQueries(String basePackageToScan, String namePattern) {
        Pattern pattern = Pattern.compile(namePattern);

        FastClasspathScanner scanner = new FastClasspathScanner(basePackageToScan);
        ScanResult scanResult = scanner.scan();
        List<String> matchedClassesNames = scanResult.getNamesOfAllClasses().stream()
                .filter(className -> pattern.matcher(className).matches())
                .collect(toList());

        List<Class<?>> matchedClasses = scanResult.classNamesToClassRefs(matchedClassesNames);
        Set<NamedQuery> namedQueries = matchedClasses.stream()
            .flatMap(matchedClass -> {
                if (matchedClass.isAnnotationPresent(NamedQuery.class)) {
                    return Stream.of(matchedClass.getAnnotation(NamedQuery.class));
                } else if (matchedClass.isAnnotationPresent(NamedQueries.class)) {
                    return Stream.of(matchedClass.getAnnotation(NamedQueries.class).value());
                } else {
                    return Stream.empty();
                }
        }).collect(toSet());

        return namedQueries;
    }

}
