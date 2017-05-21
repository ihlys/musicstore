package com.ihordev.core.jpa.testing;

import com.ihordev.core.util.GenericClass;
import com.ihordev.core.util.ReflectionsUtils;

import java.util.Map;
import java.util.function.Function;

import static com.ihordev.core.util.ReflectionsUtils.getPropertyGettersMethodsFromClass;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;


public class ReflectionDataTester<T> extends AbstractDataTester<T> {

    private Map<Property, DataTester<?>> propertyTesters;

    public ReflectionDataTester(GenericClass<T> objectsClass, DataTesterCreator dataTesterCreator,
                                Options options, int depth) {
        super(objectsClass, dataTesterCreator, options, depth);
    }

    @Override
    protected void init(GenericClass<T> objectsClass, DataTesterCreator dataTesterCreator, Options options, int depth) {
        this.propertyTesters = getPropertyGettersMethodsFromClass(objectsClass.getRawClass()).stream()
                .map(method -> new Property(method, ReflectionsUtils.getMethodGenericReturnType(method, objectsClass)))
                .collect(toMap(Function.identity(), property ->
                        createInternalDataTester(property.getPropertyClass(), dataTesterCreator, options, depth + 1)));
    }

    @SuppressWarnings("unchecked")
    @Override
    protected boolean areEqualImpl(T expectedObject, T actualObject) {
        return propertyTesters.entrySet().stream()
                .map(entry -> {
                    Property property = entry.getKey();
                    DataTester propertyTester = entry.getValue();
                    return propertyTester.areEqual(property.get(expectedObject), property.get(actualObject)); })
                .allMatch(Boolean::booleanValue);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected int getHashCodeImpl(T object) {
        return propertyTesters.entrySet().stream()
                .mapToInt(entry -> {
                    Property property = entry.getKey();
                    DataTester propertyTester = entry.getValue();
                    return propertyTester.getHashCode(property.get(object)); })
                .sum();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected String toStringImpl(T object) {
        return propertyTesters.entrySet().stream()
                .map(entry -> {
                    Property property = entry.getKey();
                    DataTester propertyTester = entry.getValue();
                    String propertyAsString = propertyTester.toString(property.get(object));
                    if (isMultilineString(propertyAsString)) {
                        return paddingString + property.getName() + ": " + System.lineSeparator() + propertyAsString;
                    } else {
                        return paddingString + property.getName() + ": " + propertyAsString;
                    }
                })
                .collect(joining(System.lineSeparator()));
    }

    private boolean isMultilineString(String str) {
        return str.contains(System.lineSeparator());
    }
}
