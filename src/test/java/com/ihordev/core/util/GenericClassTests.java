package com.ihordev.core.util;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.*;


public class GenericClassTests {

    @Test
    public void shouldRepresentGenericInformationAboutTypeCorrectly() {

        GenericClass<Map> secondLevelGenericDateParameter = new GenericClass<>(Date.class);

        GenericClass<Map> firstLevelGenericStringParameter = new GenericClass<>(String.class);
        GenericClass<Map> firstLevelGenericListParameter = new GenericClass<>(List.class, asList(secondLevelGenericDateParameter));

        GenericClass<Map> expectedGenericClass = new GenericClass<>(Map.class, asList(firstLevelGenericStringParameter,
                firstLevelGenericListParameter));
        GenericClass<Map<String, List<Date>>> actualGenericClass = new GenericClass<Map<String, List<Date>>>(){};

        Assert.assertEquals(expectedGenericClass, actualGenericClass);
    }

    @Test
    public void shouldConvertSelfToStringCorrectly() {

        String expectedStringRepresentation = "Map<String, List<Date>>";

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
}
