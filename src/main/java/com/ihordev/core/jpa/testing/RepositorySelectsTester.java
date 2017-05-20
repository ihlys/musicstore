package com.ihordev.core.jpa.testing;

import com.ihordev.core.util.GenericClass;
import com.ihordev.domainprojections.ArtistAsPageItem;
import com.ihordev.repository.ArtistRepository;
import sun.net.www.content.text.Generic;

import javax.xml.crypto.Data;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;

import static com.ihordev.core.util.ReflectionsUtils.getPropertyName;
import static java.lang.String.format;


public class RepositorySelectsTester<RepositoryType> {

    // RepositorySelectsTester<ArtistsRepository> selectsTester = new RepositorySelectsTester<>();
    // selectsTester.forRepository(ArtistsRepository.class).andMethod().findArtistsById();
    // selectsTester.test(expected);

    // RepositorySelectsTester<ArtistsRepository> selectsTester = new RepositorySelectsTester<>();
    // selectsTester.setRepository(repositoryReference);
    // selectsTester.setMethod(ArtistsRepository::select...)
    // selectsTester.test(expected, actual);

    private RepositoryType targetRepository;
    private DataTesterCreator dataTesterCreator = new DataTesterCreator();
    private DataTester<RepositoryType> dataTester;
    private Method repositorySelectMethod;

    public RepositorySelectsTester() {
        this.dataTesterCreator.addDataTester(BagDataTester.class);
        this.dataTesterCreator.addDataTester(ListDataTester.class);
        this.dataTesterCreator.addDataTester(SetDataTester.class);
    }

    public void setRepository(RepositoryType targetRepository) {
        this.targetRepository = targetRepository;
        // MethodCatcher methodCatcher = new MethodCatcher();
        // Class<?> targetRepositoryInterface = targetRepository.getClass().getInterfaces()[0];
        // Proxy.newProxyInstance(targetRepositoryInterface.getClassLoader(), new Class<?>[]{targetRepositoryInterface}, methodCatcher);
    }

    public <R> void setMethod(SelectMethod0<RepositoryType, R> selectMethod) {

    }

    public <T, R> void setMethod(SelectMethod1<RepositoryType, T, R> selectMethod) {

    }

    public <T1, T2, R> void setMethod(SelectMethod2<RepositoryType, T1, T2, R> selectMethod) {

    }

    public <T1, T2, T3, R> void setMethod(SelectMethod3<RepositoryType, T1, T2, T3, R> selectMethod) {

    }


    public void configure(GenericClass<RepositoryType> objectsUnderTestClass) {
        this.dataTester = dataTesterCreator.createForClass(objectsUnderTestClass);
    }

    public void test(RepositoryType expected) {
       // repositorySelectMethod.invoke()
    }


    private class MethodCatcher implements InvocationHandler {

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (isMethodChooserCall(method)) {
                return proxy;
            } else {
                RepositorySelectsTester.this.repositorySelectMethod = method;
                RepositorySelectsTester.this.configure(new GenericClass<>(method.getGenericReturnType()));
                return null;
            }
        }

        private boolean isMethodChooserCall(Method method) {
            return method.getName().equals("andMethod");
        }

    }

    @FunctionalInterface
    interface SelectMethod0<RepositoryType, R> {
        R execute(RepositoryType instance);
    }

    @FunctionalInterface
    interface SelectMethod1<RepositoryType, T, R> {
        R execute(RepositoryType instance, T arg);
    }

    @FunctionalInterface
    interface SelectMethod2<RepositoryType, T1, T2, R> {
        R execute(RepositoryType instance, T1 arg1, T2 arg2);
    }

    @FunctionalInterface
    interface SelectMethod3<RepositoryType, T1, T2, T3, R> {
        R execute(RepositoryType instance, T1 arg1, T2 arg2, T3 arg3);
    }

    @FunctionalInterface
    interface SelectMethod4<RepositoryType, T1, T2, T3, T4, R> {
        R execute(RepositoryType instance, T1 arg1, T2 arg2, T3 arg3, T4 arg4);
    }

    @FunctionalInterface
    interface SelectMethod5<RepositoryType, T1, T2, T3, T4, T5, R> {
        R execute(RepositoryType instance, T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5);
    }

    @FunctionalInterface
    interface SelectMethod6<RepositoryType, T1, T2, T3, T4, T5, T6, R> {
        R execute(RepositoryType instance, T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5, T6 arg6);
    }

    @FunctionalInterface
    interface SelectMethod7<RepositoryType, T1, T2, T3, T4, T5, T6, T7, R> {
        R execute(RepositoryType instance, T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5, T6 arg6, T7 arg7);
    }

    @FunctionalInterface
    interface SelectMethod8<RepositoryType, T1, T2, T3, T4, T5, T6, T7, T8, R> {
        R execute(RepositoryType instance, T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5, T6 arg6, T7 arg7, T8 arg8);
    }
}
