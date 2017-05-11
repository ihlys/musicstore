package com.ihordev.core.util;

import com.ihordev.core.navigation.WithNavigationLinks;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.Method;


public class AnnotationUtilsTests {

    @Test
    public void shouldDetermineIfClassHasWithNavigationLinksAnnotation() {

        ObjectWithAnnotatedMethod object = new ObjectWithAnnotatedMethod();
        object.handlerMethod(null);

        Method handlerMethod1 = AnnotatedObject.class.getDeclaredMethods()[0];
        Method handlerMethod2 = ObjectWithAnnotatedMethod.class.getDeclaredMethods()[0];

        Assert.assertTrue(AnnotationUtils.isWithNavigationLinks(new HandlerMethod(new AnnotatedObject(), handlerMethod1)));
        Assert.assertTrue(AnnotationUtils.isWithNavigationLinks(new HandlerMethod(new ObjectWithAnnotatedMethod(), handlerMethod2)));
    }


    @WithNavigationLinks
    public static class AnnotatedObject {

        void handlerMethod() {}
    }

    public static class ObjectWithAnnotatedMethod {

        @WithNavigationLinks
        void handlerMethod(Object obj) {}
    }
}

