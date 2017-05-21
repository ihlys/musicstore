package com.ihordev.core.jpa.testing;

import com.ihordev.core.util.GenericClass;

import java.util.Iterator;
import java.util.Map;

import static java.lang.String.format;


public abstract class AbstractIterableDataTester<E, T extends Iterable<E>> extends AbstractDataTester<T> {

    public static final String ITEM_DIVIDER = "itemDivider";

    protected String itemDivider = "-";
    protected DataTester<E> itemTester;

    public AbstractIterableDataTester(GenericClass<T> objectsClass, DataTesterCreator dataTesterCreator,
                                      Options options, int depth) {
        super(objectsClass, dataTesterCreator, options, depth);
        this.itemDivider = options.getOrDefault(ITEM_DIVIDER, itemDivider);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void init(GenericClass<T> collectionsClass, DataTesterCreator dataTesterCreator,
                        Options options, int depth) {
        Map<String, GenericClass<?>> generics = collectionsClass.getTypeVariablesGenericsMap();
        if (!generics.isEmpty()) {
            GenericClass<E> generic = (GenericClass<E>) generics.get("E");
            this.itemTester = createInternalDataTester(generic, dataTesterCreator, options, depth);
        } else {
            String errMsg = format("Cannot create collection data tester: " +
                    "declared collection type %s has no generic parameters.",
                    collectionsClass.getRawClass());
            throw new RuntimeException(errMsg);
        }
    }

    @Override
    protected int getHashCodeImpl(T iterable) {
        int hashCode = 0;
        for (E e : iterable) {
            hashCode += itemTester.getHashCode(e);
        }
        return hashCode;
    }

    @Override
    protected String toStringImpl(T iterable) {
        StringBuilder stringRepresentationBuilder = new StringBuilder();
        for (Iterator<E> iter = iterable.iterator(); iter.hasNext();) {
            stringRepresentationBuilder.append(itemTester.toString(iter.next()));
            if (iter.hasNext()) {
                stringRepresentationBuilder.append(System.lineSeparator())
                        .append(paddingString).append(itemDivider)
                        .append(System.lineSeparator());
            }
        }

        return stringRepresentationBuilder.toString();
    }
}
