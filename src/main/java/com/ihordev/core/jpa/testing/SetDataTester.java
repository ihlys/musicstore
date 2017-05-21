package com.ihordev.core.jpa.testing;

import com.ihordev.core.util.GenericClass;

import java.util.Set;


public class SetDataTester<E> extends AbstractIterableDataTester<E, Set<E>> {

    public SetDataTester(GenericClass<Set<E>> setsClass, DataTesterCreator dataTesterCreator,
                         Options options, int depth) {
        super(setsClass, dataTesterCreator, options, depth);
    }

    @Override
    protected boolean areEqualImpl(Set<E> expected, Set<E> actual) {
        if (expected.size() != actual.size()) { return false; }

        return expected.stream()
                .map(e1 -> actual.stream()
                        .map(e2 -> itemTester.areEqual(e1, e2))
                        .anyMatch(Boolean::booleanValue))
                .allMatch(Boolean::booleanValue);
    }
}
