package com.ihordev.core.jpa.testing;

import com.ihordev.core.util.GenericClass;
import net.jodah.typetools.TypeResolver;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static com.ihordev.core.util.AnnotationUtils.isJPAEntity;
import static com.ihordev.core.util.ReflectionsUtils.isClassOverridesMethod;


public class DataTesterCreator {

    private Map<Class<?>, Class<? extends AbstractDataTester<?>>> supportedDataTesters = new HashMap<>();


    public <T extends AbstractDataTester<?>> void addDataTester(Class<T> dataTesterClass) {
        supportedDataTesters.put(getDataTesterGeneric(dataTesterClass), dataTesterClass);
    }

    // resolves <X> generic argument in any "CustomDataTester implements DataTester<X>" declaration
    private <T extends DataTester<?>> Class<?> getDataTesterGeneric(Class<T> dataTesterClass) {
        try {
            Class<?> topSuperClassResolvedGeneric = TypeResolver.resolveRawArgument(DataTester.class, dataTesterClass);
            return topSuperClassResolvedGeneric;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Cannot add data tester class: ", e);
        }
    }

    public DataTesterCreator() {
        addDataTester(BagDataTester.class);
        addDataTester(ListDataTester.class);
        addDataTester(SetDataTester.class);
    }

    @SuppressWarnings("unchecked")
    public <T> DataTester<T> createForClass(GenericClass<T> objectsClass) {
        return createForClass(objectsClass, Options.noOptions(), 0);
    }

    @SuppressWarnings("unchecked")
    public <T> DataTester<T> createForClass(GenericClass<T> objectsClass, Options options) {
        return createForClass(objectsClass, options, 0);
    }

    @SuppressWarnings("unchecked")
    public <T> DataTester<T> createForClass(GenericClass<T> objectsClass, Options options, int depth) {
        Function<? super Map.Entry<Class<?>, Class<? extends AbstractDataTester<?>>>,
                Class<? extends AbstractDataTester<T>>>
                func = entry -> (Class<? extends AbstractDataTester<T>>) entry.getValue();

        Optional<Class<? extends AbstractDataTester<T>>> dataTesterClassOpt = supportedDataTesters.entrySet().stream()
                    .filter(entry -> entry.getKey() == objectsClass.getRawClass())
                    .map(func)
                    .findFirst();

        if (dataTesterClassOpt.isPresent()) {
            return createDataTester(objectsClass, dataTesterClassOpt.get(), options, depth);
        } else {
            if (isJPAEntity(objectsClass.getRawClass())) {
                return new ReflectionDataTester<>(objectsClass, this, options, depth);
            } else {
                if (objectsClass.getRawClass().isPrimitive() ||
                        !objectsClass.getRawClass().isInterface() &&
                        isClassOverridesMethod(objectsClass.getRawClass(), "equals", Object.class) &&
                        isClassOverridesMethod(objectsClass.getRawClass(), "hashCode") &&
                        isClassOverridesMethod(objectsClass.getRawClass(), "toString")) {
                    return new OverriddenDataTester<>(objectsClass, this, options, depth);
                } else {
                    return new ReflectionDataTester<>(objectsClass, this, options, depth);
                }
            }
        }
    }

    private <T> DataTester<T> createDataTester(GenericClass<T> objectsClass, Class<? extends AbstractDataTester<T>> dataTesterClass,
                                               Options options, int depth) {
        try {
            Constructor<? extends DataTester<T>> constructor = dataTesterClass
                    .getConstructor(GenericClass.class, DataTesterCreator.class, Options.class, int.class);
            return constructor.newInstance(objectsClass, this, options, depth);
        } catch (NoSuchMethodException | IllegalAccessException |
                InvocationTargetException | InstantiationException e) {
            throw new RuntimeException("Cannot create data tester: ", e);
        }
    }
}