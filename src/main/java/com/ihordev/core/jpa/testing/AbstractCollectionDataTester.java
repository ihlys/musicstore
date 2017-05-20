package com.ihordev.core.jpa.testing;

import com.ihordev.core.util.GenericClass;

import java.util.Collection;
import java.util.List;

import static com.ihordev.core.jpa.testing.Options.Option.op;
import static com.ihordev.core.jpa.testing.Options.noOptions;
import static com.ihordev.core.jpa.testing.Options.options;
import static java.lang.String.format;
import static java.util.stream.Collectors.joining;


public abstract class AbstractCollectionDataTester<E, T extends Collection<E>> extends AbstractDataTester<T> {

    public static final String ITEM_DIVIDER = "itemDivider";

    protected DataTester<E> itemTester;
    protected String itemDivider = "-";

    public AbstractCollectionDataTester(GenericClass<T> objectsClass, DataTesterCreator dataTesterCreator) {
        this(noOptions(), objectsClass, dataTesterCreator);
    }

    public AbstractCollectionDataTester(Options options, GenericClass<T> objectsClass, DataTesterCreator dataTesterCreator) {
        super(options, objectsClass, dataTesterCreator);
        this.itemDivider = options.getOrDefault(ITEM_DIVIDER, itemDivider);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void init(GenericClass<T> collectionsClass, DataTesterCreator dataTesterCreator) {
        List<GenericClass<?>> generics = collectionsClass.getGenericParametersClasses();
        if (generics != null) {
            GenericClass<E> generic = (GenericClass<E>) generics.get(0);
            this.itemTester = createInternalDataTester(generic, dataTesterCreator, options(op(DEPTH, depth)));
        } else {
            String errMsg = format("Cannot create collection data tester: " +
                    "declared collection type %s has no generic parameters.",
                    collectionsClass.getRawClass());
            throw new RuntimeException(errMsg);
        }
    }

    @Override
    protected int getHashCodeImpl(T collection) {
        return collection.stream().mapToInt(itemTester::getHashCode).sum();
    }

    @Override
    protected String toStringImpl(T collection) {
        return collection.stream()
                .map(e -> paddingString + itemTester.toString(e))
                .collect(joining(System.lineSeparator() + paddingString + itemDivider + System.lineSeparator()));
    }
}
