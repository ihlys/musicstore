package com.ihordev.core.jpa.testing;

import com.ihordev.core.util.GenericClass;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.ihordev.core.util.ReflectionsUtils.getPropertyName;


public class Property {

    private GenericClass<?> propertyClass;
    private Method method;
    private String name;

    public Property(Method method) {
        this.method = method;
        this.name = getPropertyName(method);
        this.propertyClass = new GenericClass<>(method.getGenericReturnType());
    }

    public Object get(Object propertyOwner) {
        try {
            return method.invoke(propertyOwner);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new PropertyAccessException(e);
        }
    }

    public String getName() {
        return name;
    }

    public GenericClass<?> getPropertyClass() {
        return propertyClass;
    }

}
