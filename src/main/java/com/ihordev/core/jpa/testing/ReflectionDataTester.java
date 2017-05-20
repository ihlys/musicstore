package com.ihordev.core.jpa.testing;

import com.ihordev.core.util.GenericClass;
import com.sun.xml.internal.bind.v2.runtime.output.SAXOutput;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import static com.ihordev.core.jpa.testing.Options.Option.op;
import static com.ihordev.core.jpa.testing.Options.options;
import static com.ihordev.core.util.ReflectionsUtils.getGetterMethodsFromClass;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;


public class ReflectionDataTester<T> extends AbstractDataTester<T> {

    private Map<Property, DataTester<?>> propertyTesters;

    public ReflectionDataTester(GenericClass<T> objectsClass, DataTesterCreator dataTesterCreator) {
        super(objectsClass, dataTesterCreator);
    }

    public ReflectionDataTester(Options options, GenericClass<T> objectsClass, DataTesterCreator dataTesterCreator) {
        super(options, objectsClass, dataTesterCreator);
    }

    @Override
    protected void init(GenericClass<T> objectsClass, DataTesterCreator dataTesterCreator) {
        this.propertyTesters = getGetterMethodsFromClass(objectsClass.getRawClass()).stream()
                .map(Property::new)
                .collect(toMap(Function.identity(), property ->
                        createInternalDataTester(property.getPropertyClass(),
                                dataTesterCreator, options(op("depth", depth + 1)))));
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
                    return paddingString + property.getName() + ": " + propertyTester.toString(property.get(object));
                })
                .collect(joining(System.lineSeparator()));
    }
}
