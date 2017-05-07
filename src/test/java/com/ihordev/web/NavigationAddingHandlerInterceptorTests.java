package com.ihordev.web;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.List;


public class NavigationAddingHandlerInterceptorTests {

    @Test
    public void shouldAddNavigationInfoToRequestObject() throws Exception {

        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        mockRequest.setRequestURI("/sample/other");
        mockRequest.setMethod("GET");
        MockHttpServletResponse mockResponse = new MockHttpServletResponse();

        ModelAndView mav = new ModelAndView();

        NavigationAddingHandlerInterceptor interceptor = new NavigationAddingHandlerInterceptor();
        interceptor.postHandle(mockRequest, mockResponse, null, mav);

        Assert.assertNotNull(mav.getModelMap().get("navigation"));
    }
}
