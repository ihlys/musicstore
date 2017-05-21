package com.ihordev.core.jpa.testing;

import java.util.Iterator;


public final class DataTesterUtils {

    private DataTesterUtils() {
        throw new AssertionError("DataTesterUtils cannot be instantiated.");
    }

    public static <E> boolean areOrderedIterablesEqual(Iterable<E> iterable1, Iterable<E> iterable2,
                                                       DataTester<E> itemTester) {
        Iterator<E> iter1 = iterable1.iterator();
        Iterator<E> iter2 = iterable2.iterator();

        while (iter1.hasNext() && iter2.hasNext()) {
            E list1Item = iter1.next();
            E list2Item = iter2.next();
            if (!(list1Item == null ? list2Item == null : itemTester.areEqual(list1Item, list2Item)))
                return false;
        }
        return true;
    }

}
