package com.ihordev.core.jpa.testing;

import com.ihordev.core.util.GenericClass;


public class OverriddenDataTester<T> extends AbstractDataTester<T> {

    public OverriddenDataTester(GenericClass<T> objectsClass, DataTesterCreator dataTesterCreator, Options options, int depth) {
        super(objectsClass, dataTesterCreator, options, depth);
    }

    @Override
    protected void init(GenericClass<T> objectsClass, DataTesterCreator dataTesterCreator, Options options, int depth) {

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
