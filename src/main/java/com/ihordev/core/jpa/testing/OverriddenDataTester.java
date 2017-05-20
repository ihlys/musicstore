package com.ihordev.core.jpa.testing;

import com.ihordev.core.util.GenericClass;


public class OverriddenDataTester<T> extends AbstractDataTester<T> {

    public OverriddenDataTester(GenericClass<T> objectsClass, DataTesterCreator dataTesterCreator) {
        super(objectsClass, dataTesterCreator);
    }

    public OverriddenDataTester(Options options, GenericClass<T> objectsClass, DataTesterCreator dataTesterCreator) {
        super(options, objectsClass, dataTesterCreator);
    }

    @Override
    protected void init(GenericClass<T> objectsClass, DataTesterCreator dataTesterCreator) {

    }

    @Override
    protected boolean areEqualImpl(T expected, T actual) {
        return expected.equals(actual);
    }

    @Override
    protected int getHashCodeImpl(T object) {
        return object.hashCode();
    }

    @Override
    protected String toStringImpl(T object) {
        return object.toString();
    }
}
