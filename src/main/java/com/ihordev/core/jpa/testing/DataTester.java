package com.ihordev.core.jpa.testing;



public interface DataTester<T> {

    boolean areEqual(T expected, T actual);

    int getHashCode(T object);

    String toString(T object);
}
