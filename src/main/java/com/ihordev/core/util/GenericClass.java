package com.ihordev.core.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static java.util.stream.Collectors.joining;


public class GenericClass<T> {

    private Class<?> rawClass;
    private List<GenericClass<?>> genericParametersClasses;

    public Class<?> getRawClass() {
        return rawClass;
    }

    public List<GenericClass<?>> getGenericParametersClasses() {
        return genericParametersClasses;
    }

    public GenericClass(Class<?> rawClass) {
        this.rawClass = rawClass;
    }

    public GenericClass(Class<?> rawClass, List<GenericClass<?>> genericParametersClasses) {
        this.rawClass = rawClass;
        this.genericParametersClasses = genericParametersClasses;
    }

    public GenericClass() {
        Type instanceType = getInstanceType();
        initForType(instanceType);
    }

    public GenericClass(Type type) {
        initForType(type);
    }

    private void initForType(Type type) {
        if (type instanceof ParameterizedType) {
            ParameterizedType instanceParametrizedType = (ParameterizedType) type;
            this.rawClass = (Class<?>) instanceParametrizedType.getRawType();
            this.genericParametersClasses = getGenerics(instanceParametrizedType);
        } else {
            throwExceptionIfNotAClass(type);
            this.rawClass = (Class<?>) type;
        }
    }

    // return <T> part in any GenericClass<T> declaration
    private Type getInstanceType() {
        Type superclassType = this.getClass().getGenericSuperclass();
        if (superclassType instanceof ParameterizedType) {
            return ((ParameterizedType) superclassType).getActualTypeArguments()[0];
        } else {
            throw new IllegalArgumentException("Cannot create GenericClass instance without any type specified.");
        }
    }

    private List<GenericClass<?>> getGenerics(ParameterizedType type) {
        Type[] typeGenerics = type.getActualTypeArguments();

        List<GenericClass<?>> typeGenericsClasses = new ArrayList<>();
        for (Type typeGeneric : typeGenerics) {
            if (typeGeneric instanceof ParameterizedType) {
                ParameterizedType typeParametrizedGeneric = (ParameterizedType) typeGeneric;
                Type typeParametrizedGenericRawType = typeParametrizedGeneric.getRawType();
                List<GenericClass<?>> typeParametrizedGenericInnerGenerics = getGenerics(typeParametrizedGeneric);
                typeGenericsClasses.add(new GenericClass<>((Class<?>) typeParametrizedGenericRawType,
                        typeParametrizedGenericInnerGenerics));
            } else {
                throwExceptionIfNotAClass(typeGeneric);
                typeGenericsClasses.add(new GenericClass<>((Class<?>) typeGeneric));
            }
        }

        return typeGenericsClasses;
    }

    private  void throwExceptionIfNotAClass(Type type) {
        if (!(type instanceof Class)) {
            String errMsg = format("Cannot create GenericClass instance: declared generic parameter %s is not a class.",
                    type.getTypeName());
            throw new IllegalArgumentException(errMsg);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GenericClass)) return false;

        GenericClass<?> that = (GenericClass<?>) o;

        if (!getRawClass().equals(that.getRawClass())) return false;
        return getGenericParametersClasses() != null ? getGenericParametersClasses().equals(that.getGenericParametersClasses()) : that.getGenericParametersClasses() == null;
    }

    @Override
    public int hashCode() {
        int result = getRawClass().hashCode();
        result = 31 * result + (getGenericParametersClasses() != null ? getGenericParametersClasses().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        String baseString = rawClass.getSimpleName();

        if (genericParametersClasses != null) {
            String genericsString = genericParametersClasses.stream()
                    .map(GenericClass::toString)
                    .collect(joining(", "));
            baseString += "<%s>";
            return format(baseString, genericsString);
        } else {
            return baseString;
        }
    }
}
