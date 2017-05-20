package com.ihordev.core.jpa.testing;

import com.ihordev.core.util.GenericClass;

import java.util.*;

import static com.ihordev.core.jpa.testing.Options.noOptions;


public abstract class AbstractDataTester<T> implements DataTester<T> {

    public static final Map<GenericClass<?>, AbstractDataTester<?>> dataTestersRegistry = new HashMap<>();

    public static final Set<Object> expectedObjectReferencesRegistry = Collections.newSetFromMap(new IdentityHashMap<>());
    public static final Set<Object> actualObjectReferencesRegistry = Collections.newSetFromMap(new IdentityHashMap<>());
    public static final Set<Object> objectReferencesRegistry = Collections.newSetFromMap(new IdentityHashMap<>());

    public static final String DEPTH = "depth";
    public static final String PADDING_MULTIPLIER = "paddingMultiplier";

    protected int depth = 0;
    protected String paddingString = " ";

    public AbstractDataTester(GenericClass<T> objectsClass, DataTesterCreator dataTesterCreator) {
        this(noOptions(), objectsClass, dataTesterCreator);
    }

    public AbstractDataTester(Options options, GenericClass<T> objectsClass,
                              DataTesterCreator dataTesterCreator) {
        this.depth = options.getOrDefault(DEPTH, depth);
        int paddingMultiplier = options.getOrDefault(PADDING_MULTIPLIER, 2);
        this.paddingString = String.join("", Collections.nCopies(depth * paddingMultiplier, paddingString));

        dataTestersRegistry.put(objectsClass, this);
        try {
            init(objectsClass, dataTesterCreator);
        } finally {
            dataTestersRegistry.remove(objectsClass);
        }
    }

    protected abstract void init(GenericClass<T> objectsClass, DataTesterCreator dataTesterCreator);

    @SuppressWarnings("unchecked")
    protected <S> DataTester<S> createInternalDataTester(GenericClass<S> objectsClass,
                                                     DataTesterCreator dataTesterCreator,
                                                     Options options) {
        if (dataTestersRegistry.containsKey(objectsClass)) {
            return (AbstractDataTester<S>) dataTestersRegistry.get(objectsClass);
        } else {
            return dataTesterCreator.createForClass(objectsClass, options);
        }
    }

    @Override
    public boolean areEqual(T expected, T actual) {
        if (expected == actual) {
            return true;
        }
        if (expected == null || actual == null) {
            return false;
        }

        if (expectedObjectReferencesRegistry.contains(expected) ||
            actualObjectReferencesRegistry.contains(actual)) {
            return true;
        }

        expectedObjectReferencesRegistry.add(expected);
        actualObjectReferencesRegistry.add(actual);

        try {
            return areEqualImpl(expected, actual);
        } finally {
            expectedObjectReferencesRegistry.remove(expected);
            actualObjectReferencesRegistry.remove(actual);
        }
    }

    protected abstract boolean areEqualImpl(T expected, T actual);

    @Override
    public int getHashCode(T object) {
        if (object == null) {
            return 0;
        } else {
            if (objectReferencesRegistry.contains(object)) {
                return 0;
            }

            objectReferencesRegistry.add(object);

            try {
                return getHashCodeImpl(object);
            } finally {
                objectReferencesRegistry.remove(object);
            }
        }
    }

    protected abstract int getHashCodeImpl(T object);

    @Override
    public String toString(T object) {
        if (object == null) {
            return "null";
        } else {
            if (objectReferencesRegistry.contains(object)) {
                return "circular reference";
            }

            objectReferencesRegistry.add(object);

            try {
                return toStringImpl(object);
            } finally {
                objectReferencesRegistry.remove(object);
            }
        }
    }

    protected abstract String toStringImpl(T object);
}
