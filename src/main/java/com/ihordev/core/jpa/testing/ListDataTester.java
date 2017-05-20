package com.ihordev.core.jpa.testing;


import com.ihordev.core.util.GenericClass;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;


public class ListDataTester<E> extends AbstractCollectionDataTester<E, List<E>> {

    public static final String RE_SORT_LISTS = "reSortLists";

    private boolean reSortLists;

    public ListDataTester(GenericClass<List<E>> listsClass, DataTesterCreator dataTesterCreator) {
        super(listsClass, dataTesterCreator);
    }

    public ListDataTester(Options options, GenericClass<List<E>> listsClass,
                          DataTesterCreator dataTesterCreator) {
        super(options, listsClass, dataTesterCreator);
        this.reSortLists = options.getOrDefault(RE_SORT_LISTS, false);
    }

    @Override
    protected boolean areEqualImpl(List<E> expectedList, List<E> actualList) {
        if (expectedList.size() != actualList.size()) { return false; }

        if (reSortLists) {
            Comparator<E> hashCodeComparator = (obj1, obj2) ->
                    Integer.compare(itemTester.getHashCode(obj1), itemTester.getHashCode(obj2));

            Collections.sort(expectedList, hashCodeComparator);
            Collections.sort(actualList, hashCodeComparator);
        }

        ListIterator<E> iter1 = expectedList.listIterator();
        ListIterator<E> iter2 = actualList.listIterator();

        while (iter1.hasNext() && iter2.hasNext()) {
            E list1Item = iter1.next();
            E list2Item = iter2.next();
            if (!(list1Item == null ? list2Item == null : itemTester.areEqual(list1Item, list2Item)))
                return false;
        }
        return true;
    }

}
