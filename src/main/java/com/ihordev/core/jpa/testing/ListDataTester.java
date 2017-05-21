package com.ihordev.core.jpa.testing;


import com.ihordev.core.util.GenericClass;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.ihordev.core.jpa.testing.DataTesterUtils.areOrderedIterablesEqual;


public class ListDataTester<E> extends AbstractIterableDataTester<E, List<E>> {

    public static final String RE_SORT_LISTS = "reSortLists";

    private boolean reSortLists = false;

    public ListDataTester(GenericClass<List<E>> listsClass, DataTesterCreator dataTesterCreator,
                          Options options, int depth) {
        super(listsClass, dataTesterCreator, options, depth);
        this.reSortLists = options.getOrDefault(RE_SORT_LISTS, reSortLists);
    }

    @Override
    protected boolean areEqualImpl(List<E> expected, List<E> actual) {
        if (expected.size() != actual.size()) { return false; }

        if (reSortLists) {
            Comparator<E> hashCodeComparator = (obj1, obj2) ->
                    Integer.compare(itemTester.getHashCode(obj1), itemTester.getHashCode(obj2));

            Collections.sort(expected, hashCodeComparator);
            Collections.sort(actual, hashCodeComparator);
        }

        return areOrderedIterablesEqual(expected, actual, itemTester);
    }

}
