package com.ihordev.core.jpa.testing;

import com.ihordev.core.util.GenericClass;
import info.solidsoft.mockito.java8.api.WithBDDMockito;
import org.junit.Assert;
import org.junit.Test;


public class ReflectionDataTesterTests implements WithBDDMockito {

    static class ObjectWithProperties {

        private ObjectWithProperties objectWithPropertiesMock;

        public ObjectWithProperties(ObjectWithProperties objectWithPropertiesMock) {
            this.objectWithPropertiesMock = objectWithPropertiesMock;
        }

        String getStringProperty() {
            return objectWithPropertiesMock.getStringProperty();
        }

        int getIntegerProperty() { return objectWithPropertiesMock.getIntegerProperty(); }
    }


    private ObjectWithProperties createObjectWithPropertiesMock(String stringPropertyValue, int integerPropertyValue) {
        ObjectWithProperties objectWithPropertiesMock = mock(ObjectWithProperties.class);
        given(objectWithPropertiesMock.getStringProperty()).willReturn(stringPropertyValue);
        given(objectWithPropertiesMock.getIntegerProperty()).willReturn(integerPropertyValue);
        return objectWithPropertiesMock;
    }

    @Test
    public void shouldTestIfTwoObjectsAreEqualCorrectly() {
        String sameString = "test string";
        int sameInteger = 10;

        ObjectWithProperties objectWithProperties1_mock = createObjectWithPropertiesMock(sameString, sameInteger);
        ObjectWithProperties objectWithProperties1_stub = new ObjectWithProperties(objectWithProperties1_mock);

        ObjectWithProperties objectWithProperties2_mock = createObjectWithPropertiesMock(sameString, sameInteger);
        ObjectWithProperties objectWithProperties2_stub = new ObjectWithProperties(objectWithProperties2_mock);

        DataTesterCreator dataTesterCreator = new DataTesterCreator();
        DataTester<ObjectWithProperties> tester =
                dataTesterCreator.createForClass(new GenericClass<ObjectWithProperties>(){});
        boolean areEqual = tester.areEqual(objectWithProperties1_stub, objectWithProperties2_stub);

        then(objectWithProperties1_mock).should(times(1)).getStringProperty();
        then(objectWithProperties1_mock).should(times(1)).getIntegerProperty();
        then(objectWithProperties1_mock).shouldHaveNoMoreInteractions();
        then(objectWithProperties2_mock).should(times(1)).getStringProperty();
        then(objectWithProperties2_mock).should(times(1)).getIntegerProperty();
        then(objectWithProperties2_mock).shouldHaveNoMoreInteractions();

        Assert.assertTrue(areEqual);
    }

    @Test
    public void shouldComputeHashCodeCorrectly() {
        String sameString = "test string";
        int sameInteger = 10;
        int expectedHashCode = sameString.hashCode() + 10;

        ObjectWithProperties objectWithPropertiesMock = createObjectWithPropertiesMock(sameString, sameInteger);
        ObjectWithProperties objectWithPropertiesStub = new ObjectWithProperties(objectWithPropertiesMock);

        DataTesterCreator dataTesterCreator = new DataTesterCreator();
        DataTester<ObjectWithProperties> tester =
                dataTesterCreator.createForClass(new GenericClass<ObjectWithProperties>(){});
        int actualHashCode = tester.getHashCode(objectWithPropertiesStub);

        then(objectWithPropertiesMock).should(times(1)).getStringProperty();
        then(objectWithPropertiesMock).should(times(1)).getIntegerProperty();
        then(objectWithPropertiesMock).shouldHaveNoMoreInteractions();

        Assert.assertEquals(expectedHashCode, actualHashCode);
    }

    @Test
    public void shouldConvertToStringCorrectly() {
        String sameString = "test string";
        int sameInteger = 10;

        ObjectWithProperties objectWithPropertiesMock = createObjectWithPropertiesMock(sameString, sameInteger);
        ObjectWithProperties objectWithPropertiesStub = new ObjectWithProperties(objectWithPropertiesMock);

        DataTesterCreator dataTesterCreator = new DataTesterCreator();
        DataTester<ObjectWithProperties> tester =
                dataTesterCreator.createForClass(new GenericClass<ObjectWithProperties>(){});
        String actualStringRepresentation = tester.toString(objectWithPropertiesStub);

        then(objectWithPropertiesMock).should(times(1)).getStringProperty();
        then(objectWithPropertiesMock).should(times(1)).getIntegerProperty();
        then(objectWithPropertiesMock).shouldHaveNoMoreInteractions();

        System.out.println(actualStringRepresentation);
    }
}
