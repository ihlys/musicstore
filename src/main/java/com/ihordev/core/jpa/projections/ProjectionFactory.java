package com.ihordev.core.jpa.projections;

import com.ihordev.core.jpa.testing.Property;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.ihordev.core.util.ReflectionsUtils.getGetterMethodsFromClass;
import static com.ihordev.core.util.ReflectionsUtils.getPropertyName;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;


public class ProjectionFactory<T> {

    private Class<T> interfaceClass;
    private List<Property> properties;

    public ProjectionFactory(Class<T> interfaceClass) {
        this.interfaceClass = interfaceClass;
        this.properties = getGetterMethodsFromClass(interfaceClass).stream()
                .map(Property::new)
                .collect(toList());
        if (properties.isEmpty()) {
            throw new IllegalArgumentException("Cannot create projection factory for interface without properties.");
        }
    }

    @SuppressWarnings("unchecked")
    public T createProjection(Map<String, Object> propertiesValues) {
        checkIfAllPropertiesAreSet(propertiesValues);
        ProxyHandler proxyHandler = new ProxyHandler(propertiesValues);
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, proxyHandler);
    }

    private void checkIfAllPropertiesAreSet(Map<String, Object> propertiesValues) {
        Set<String> propertiesThatAreNotSet = getPropertiesThatAreNotSet(propertiesValues);
        if (!propertiesThatAreNotSet.isEmpty()) {
            String errMsg = format("Cannot create projection: properties %s are not set.", propertiesThatAreNotSet);
            throw new IllegalArgumentException(errMsg);
        }
    }

    private Set<String> getPropertiesThatAreNotSet(Map<String, Object> propertiesValues) {
        Set<String> propertiesNames = properties.stream()
                .map(Property::getName)
                .collect(toSet());
        propertiesNames.removeAll(propertiesValues.keySet());
        return propertiesNames;
    }

    private static class ProxyHandler implements InvocationHandler {

        private Map<String, Object> propertiesValues = new HashMap<>();

        public ProxyHandler(Map<String, Object> propertiesValues) {
            this.propertiesValues = propertiesValues;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getName().startsWith("get")) {
                String propertyName = getPropertyName(method);
                return propertiesValues.get(propertyName);
            } else {
                String errMsg = format("There is no handling for method with name %s", method.getName());
                throw new RuntimeException(errMsg);
            }
        }

    }
}
