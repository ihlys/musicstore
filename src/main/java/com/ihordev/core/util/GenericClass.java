package com.ihordev.core.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.lang.String.format;
import static java.util.stream.Collectors.joining;


public class GenericClass<T> {

    private Class<?> rawClass;
    private LinkedHashMap<String, GenericClass<?>> typeVariablesGenericsMap = new LinkedHashMap<>();

    public Class<?> getRawClass() {
        return rawClass;
    }

    public Map<String, GenericClass<?>> getTypeVariablesGenericsMap() {
        return typeVariablesGenericsMap;
    }

    public GenericClass(Class<?> rawClass) {
        this.rawClass = rawClass;
    }

    GenericClass(Class<?> rawClass, LinkedHashMap<String, GenericClass<?>> typeVariablesGenericsMap) {
        this.rawClass = rawClass;
        this.typeVariablesGenericsMap = typeVariablesGenericsMap;
    }

    public GenericClass() {
        Type classType = getClassType();
        if (classType instanceof ParameterizedType) {
            ParameterizedType classParametrizedType = (ParameterizedType) classType;
            this.rawClass = (Class<?>) classParametrizedType.getRawType();
            this.typeVariablesGenericsMap = getTypeVariablesGenericsMap(classParametrizedType, null);
        } else {
            throwExceptionIfNotAClass(classType);
            this.rawClass = (Class<?>) classType;
        }
    }

    // return <T> part in any GenericClass<T> declaration
    private Type getClassType() {
        Type superclassType = this.getClass().getGenericSuperclass();
        if (superclassType instanceof ParameterizedType) {
            return ((ParameterizedType) superclassType).getActualTypeArguments()[0];
        } else {
            throw new IllegalArgumentException("Cannot create GenericClass instance without any type specified.");
        }
    }

    public static GenericClass<?> createForClassMemberType(ParameterizedType memberType, GenericClass<?> memberOwner) {
        return new GenericClass<>(memberType, memberOwner.typeVariablesGenericsMap);
    }

    public static GenericClass<?> createForType(ParameterizedType type) {
        return new GenericClass<>(type, null);
    }

    private GenericClass(ParameterizedType type, LinkedHashMap<String, GenericClass<?>> resolvedTypeVariables) {
        this.rawClass = (Class<?>) type.getRawType();
        this.typeVariablesGenericsMap = getTypeVariablesGenericsMap(type, resolvedTypeVariables);
    }

    private static LinkedHashMap<String, GenericClass<?>> getTypeVariablesGenericsMap(ParameterizedType type,
                                                      LinkedHashMap<String, GenericClass<?>> resolvedTypeVariables) {
        TypeVariable<?>[] typeVariables = getRawClassTypeVariables(type);
        Type[] typeValues = type.getActualTypeArguments();

        LinkedHashMap<String, GenericClass<?>> typeVariablesGenericsMap = new LinkedHashMap<>();

        for (int i = 0; i < typeValues.length; i++) {
            String typeVariableName = typeVariables[i].getName();
            Type typeVariableValue = typeValues[i];
            if (typeVariableValue instanceof ParameterizedType) {
                typeVariablesGenericsMap.put(typeVariableName,
                        new GenericClass<>((ParameterizedType) typeVariableValue, resolvedTypeVariables));
            } else if (typeVariableValue instanceof TypeVariable &&
                    resolvedTypeVariables != null &&
                    resolvedTypeVariables.containsKey(typeVariableValue.getTypeName())) {
                typeVariablesGenericsMap.put(typeVariableName, resolvedTypeVariables.get(typeVariableValue.getTypeName()));
            } else {
                throwExceptionIfNotAClass(typeVariableValue);
                typeVariablesGenericsMap.put(typeVariableName, new GenericClass<>((Class<?>) typeVariableValue));
            }
        }

        return typeVariablesGenericsMap;
    }

    private static TypeVariable<?>[] getRawClassTypeVariables(ParameterizedType type) {
        return ((Class<?>) type.getRawType()).getTypeParameters();
    }

    private static void throwExceptionIfNotAClass(Type type) {
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

        if (!rawClass.equals(that.rawClass)) return false;
        return typeVariablesGenericsMap.equals(that.typeVariablesGenericsMap);
    }

    @Override
    public int hashCode() {
        int result = rawClass.hashCode();
        result = 31 * result + typeVariablesGenericsMap.hashCode();
        return result;
    }

    @Override
    public String toString() {
        String baseString = rawClass.getSimpleName();

        if (!typeVariablesGenericsMap.isEmpty()) {
            String genericsString = typeVariablesGenericsMap.entrySet().stream()
                    .map(entry -> entry.getKey() + ":" + entry.getValue().toString())
                    .collect(joining(", "));
            baseString += "<%s>";
            return format(baseString, genericsString);
        } else {
            return baseString;
        }
    }

}
