package com.ihordev.core.jpa.testing;

import com.ihordev.core.util.GenericClass;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.ihordev.core.jpa.testing.DataTesterUtils.areOrderedIterablesEqual;


public class BagDataTester<E> extends AbstractIterableDataTester<E, Collection<E>> {

    public BagDataTester(GenericClass<Collection<E>> bagsClass, DataTesterCreator dataTesterCreator,
                         Options options, int depth) {
        super(bagsClass, dataTesterCreator, options, depth);
    }

    @Override
    protected boolean areEqualImpl(Collection<E> expectedBag, Collection<E> actualBag) {
        if (expectedBag.size() != actualBag.size()) { return false; }

        Comparator<E> hashCodeComparator = (obj1, obj2) ->
                Integer.compare(itemTester.getHashCode(obj1), itemTester.getHashCode(obj2));

        Collections.sort((List<E>) expectedBag, hashCodeComparator);
        Collections.sort((List<E>) actualBag, hashCodeComparator);

        return areOrderedIterablesEqual(expectedBag, actualBag, itemTester);
    }
}
