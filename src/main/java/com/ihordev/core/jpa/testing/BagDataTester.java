package com.ihordev.core.jpa.testing;

import com.ihordev.core.util.GenericClass;

import java.util.*;


public class BagDataTester<E> extends AbstractCollectionDataTester<E, Collection<E>> {

    public BagDataTester(GenericClass<Collection<E>> bagsClass, DataTesterCreator dataTesterCreator) {
        super(bagsClass, dataTesterCreator);
    }

    public BagDataTester(Options options, GenericClass<Collection<E>> bagsClass,
                         DataTesterCreator dataTesterCreator) {
        super(options, bagsClass, dataTesterCreator);
    }

    @Override
    protected boolean areEqualImpl(Collection<E> expectedBag, Collection<E> actualBag) {
        if (expectedBag.size() != actualBag.size()) { return false; }

        Comparator<E> hashCodeComparator = (obj1, obj2) ->
                Integer.compare(itemTester.getHashCode(obj1), itemTester.getHashCode(obj2));

        Collections.sort((List<E>) expectedBag, hashCodeComparator);
        Collections.sort((List<E>) actualBag, hashCodeComparator);

        return checkSortedListsEquality((List<E>) expectedBag, (List<E>) actualBag);
    }

    private boolean checkSortedListsEquality(List<E> list1, List<E> list2) {
        ListIterator<E> iter1 = list1.listIterator();
        ListIterator<E> iter2 = list2.listIterator();

        while (iter1.hasNext() && iter2.hasNext()) {
            E list1Item = iter1.next();
            E list2Item = iter2.next();
            if (!(list1Item == null ? list2Item == null : itemTester.areEqual(list1Item, list2Item)))
                return false;
        }
        return true;
    }
}
