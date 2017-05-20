package com.ihordev.core.jpa.testing;

import com.ihordev.core.util.GenericClass;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;


public class DataTesterCreatorTests {

    static class CustomType {

        private String stringProperty;

        public CustomType(String stringProperty) {
            this.stringProperty = stringProperty;
        }

        public String getStringProperty() {
            return stringProperty;
        }
    }

    static class CustomDataTester extends AbstractDataTester<CustomType> {

        public CustomDataTester(GenericClass<CustomType> objectsClass, DataTesterCreator dataTesterCreator) {
            super(objectsClass, dataTesterCreator);
        }

        public CustomDataTester(Options options, GenericClass<CustomType> objectsClass,
                                DataTesterCreator dataTesterCreator) {
            super(options, objectsClass, dataTesterCreator);
        }

        @Override
        protected void init(GenericClass<CustomType> objectsClass, DataTesterCreator dataTesterCreator) {

        }

        @Override
        protected boolean areEqualImpl(CustomType expected, CustomType actual) {
            return false;
        }

        @Override
        protected int getHashCodeImpl(CustomType object) {
            return 0;
        }

        @Override
        protected String toStringImpl(CustomType object) {
            return null;
        }
    }

    @Test
    public void shouldCreateReflectionDataTesterIfTypeIsUnknownAndEqualsHashcodeToStringIsNotOverridden() {
        DataTesterCreator dataTesterCreator = new DataTesterCreator();
        DataTester<CustomType> dataTester = dataTesterCreator
                .createForClass(new GenericClass<CustomType>(){});

        Assert.assertEquals(ReflectionDataTester.class, dataTester.getClass());
    }

    @Test
    public void shouldCreateCustomDataTesterWhenItIsRegistered() {
        DataTesterCreator dataTesterCreator = new DataTesterCreator();
        dataTesterCreator.addDataTester(CustomDataTester.class);
        DataTester<CustomType> dataTester = dataTesterCreator
                .createForClass(new GenericClass<CustomType>(){});

        Assert.assertEquals(CustomDataTester.class, dataTester.getClass());
    }

}
