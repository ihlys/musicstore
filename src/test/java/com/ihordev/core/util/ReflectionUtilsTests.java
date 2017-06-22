package com.ihordev.core.util;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;

import static com.ihordev.core.util.ReflectionsUtils.*;
import static java.util.stream.Collectors.toSet;

public class ReflectionUtilsTests {

    static class SuperClass {

        private String propertyOne;

        public String getPropertyOne() {
            return propertyOne;
        }
    }

    static class SomeClass extends SuperClass {

        private String propertyTwo;

        public String getPropertyTwo() {
            return propertyTwo;
        }
    }

    @Test
    public void shouldDetermineIfMethodIsPropertyGetterCorrectly() throws NoSuchMethodException {
        Method propertyGetter = SomeClass.class.getMethod("getPropertyTwo");
        Method toStringMethod = SomeClass.class.getMethod("toString");

        Assert.assertTrue(isPropertyGetter(propertyGetter));
        Assert.assertFalse(isPropertyGetter(toStringMethod));
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldThrowExceptionIfMethodIsNotPropertyGetter() throws NoSuchMethodException {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Cannot get property name from method with name toString," +
                " method is not property getter.");

        Method toStringMethod = SomeClass.class.getMethod("toString");

        getPropertyName(toStringMethod);
    }

    @Test
    public void shouldGetPropertyNameCorrectly() throws NoSuchMethodException {
        Method propertyGetter = SomeClass.class.getMethod("getPropertyTwo");

        String propertyName = getPropertyName(propertyGetter);

        Assert.assertEquals("propertyTwo", propertyName);
    }

    @Test
    public void shouldGetPropertyGettersMethodsFromClassCorrectly() {
        Set<Method> propertyGetters = getPropertyGettersMethodsFromClass(SomeClass.class);

        Set<String> propertyGettersNames = propertyGetters.stream().map(Method::getName).collect(toSet());

        Assert.assertTrue(propertyGettersNames.contains("getPropertyOne"));
        Assert.assertTrue(propertyGettersNames.contains("getPropertyTwo"));
    }

    @Test
    public void shouldGetSortedPropertyGettersMethodsFromClassCorrectly() {
        SortedSet<Method> propertyGettersSorted = getPropertyGettersMethodsFromClass(SomeClass.class,
                Comparator.comparing(Method::getName));

        Iterator<Method> iterator = propertyGettersSorted.iterator();
        Assert.assertEquals("getPropertyOne", iterator.next().getName());
        Assert.assertEquals("getPropertyTwo", iterator.next().getName());
    }

    @Test
    public void shouldGetPropertiesNamesFromClassCorrectly() {
        Set<String> propertiesNames = getPropertiesNames(SomeClass.class);

        Assert.assertTrue(propertiesNames.contains("propertyOne"));
        Assert.assertTrue(propertiesNames.contains("propertyTwo"));
    }

    @Test
    public void shouldGetSortedPropertiesNamesFromClassCorrectly() {
        SortedSet<String> propertiesNamesSorted = getPropertiesNames(SomeClass.class,
                Comparator.naturalOrder());

        Iterator<String> iterator = propertiesNamesSorted.iterator();
        Assert.assertEquals("propertyOne", iterator.next());
        Assert.assertEquals("propertyTwo", iterator.next());
    }
}
