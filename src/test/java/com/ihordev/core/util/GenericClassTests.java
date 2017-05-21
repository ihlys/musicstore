package com.ihordev.core.util;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import sun.net.www.content.text.Generic;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;


public class GenericClassTests {


    @Test
    public void shouldCaptureGenericParametersArgumentsCorrectly() {
        GenericClass<Date> genericDateParameter = new GenericClass<>(Date.class);

        GenericClass<Map> genericStringParameter = new GenericClass<>(String.class);

        LinkedHashMap<String, GenericClass<?>> listGenericParametersClasses = new LinkedHashMap<>();
        listGenericParametersClasses.put("E", genericDateParameter);
        GenericClass<Map> genericListParameter = new GenericClass<>(List.class, listGenericParametersClasses);

        LinkedHashMap<String, GenericClass<?>> mapGenericParametersClasses = new LinkedHashMap<>();
        mapGenericParametersClasses.put("K", genericStringParameter);
        mapGenericParametersClasses.put("V", genericListParameter);
        GenericClass<Map<String, List<Date>>> expectedGenericClass = new GenericClass<>(Map.class, mapGenericParametersClasses);

        GenericClass<Map<String, List<Date>>> actualGenericClass = new GenericClass<Map<String, List<Date>>>(){};

        Assert.assertEquals(expectedGenericClass, actualGenericClass);
    }

    @Test
    public void shouldConvertSelfToStringCorrectly() {

        String expectedStringRepresentation = "Map<K:String, V:List<E:Date>>";

        GenericClass<Map<String, List<Date>>> actualGenericClass = new GenericClass<Map<String, List<Date>>>(){};
        String actualStringRepresentation = actualGenericClass.toString();

        System.out.println(actualStringRepresentation);

        Assert.assertEquals(expectedStringRepresentation, actualStringRepresentation);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldThrowExceptionWhenClassTypeIsNotSpecified() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Cannot create GenericClass instance without any type specified.");

        new GenericClass(){};
    }

    @Test
    public void shouldThrowExceptionWhenSpecifiedClassTypeIsNotAClass() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Cannot create GenericClass instance: declared generic parameter T is not a class.");

        createGenericClassWithTypeVariableAsClass();
    }

    private <T> void createGenericClassWithTypeVariableAsClass() {
        new GenericClass<T>(){};
    }

    @Test
    public void shouldThrowExceptionWhenAnySpecifiedGenericParameterIsNotAClass() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Cannot create GenericClass instance: declared generic parameter T is not a class.");

        createGenericClassWithTypeVariableAsGenericParameter();
    }

    private <T> void createGenericClassWithTypeVariableAsGenericParameter() {
        new GenericClass<List<T>>(){};
    }

    @Test
    public void shouldCaptureGenericParametersArgumentsInClassMemberCorrectly() throws NoSuchFieldException {
        GenericClass<Map<String, List<Set<String>>>> expectedFieldGenericClass =
                new GenericClass<Map<String, List<Set<String>>>>(){};

        GenericClass<ClassWithGenericsAndMember<Set<String>>> classWithMemberGenericClass =
                new GenericClass<ClassWithGenericsAndMember<Set<String>>>(){};

        Field classWithMemberField = ClassWithGenericsAndMember.class.getField("field");
        Type classWithMemberFieldGenericType = classWithMemberField.getGenericType();

        GenericClass<?> actualFieldGenericClass = GenericClass.createForClassMemberType(
                (ParameterizedType) classWithMemberFieldGenericType, classWithMemberGenericClass);

        Assert.assertEquals(expectedFieldGenericClass, actualFieldGenericClass);
    }

    static class ClassWithGenericsAndMember<T> {

        public Map<String, List<T>> field;
    }
}
