package com.ihordev.core.jpa.testing;

import com.ihordev.core.util.GenericClass;

import java.util.Set;


public class SetDataTester<E> extends AbstractCollectionDataTester<E, Set<E>> {

    public SetDataTester(GenericClass<Set<E>> setsClass, DataTesterCreator dataTesterCreator) {
        super(setsClass, dataTesterCreator);
    }

    public SetDataTester(Options options, GenericClass<Set<E>> setsClass,
                         DataTesterCreator dataTesterCreator) {
        super(options, setsClass, dataTesterCreator);
    }

    @Override
    protected boolean areEqualImpl(Set<E> expectedSet, Set<E> actualSet) {
        if (expectedSet.size() != actualSet.size()) { return false; }

        return expectedSet.stream()
                .map(e1 -> actualSet.stream()
                        .map(e2 -> itemTester.areEqual(e1, e2))
                        .anyMatch(Boolean::booleanValue))
                .allMatch(Boolean::booleanValue);
    }
}
