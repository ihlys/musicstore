package com.ihordev.web.interceptors;

import com.ihordev.core.navigation.Navigation;
import com.ihordev.core.navigation.NavigationLink;
import com.ihordev.core.navigation.WithNavigationLinks;
import com.ihordev.web.AbstractMockMvcTests;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@WebMvcTest(controllers = NavigationAddingHandlerInterceptorTests.StubController.class,
            includeFilters = {@Filter(type = FilterType.ASSIGNABLE_TYPE,
                                      classes = NavigationAddingHandlerInterceptor.class)})
public class NavigationAddingHandlerInterceptorTests extends AbstractMockMvcTests {

    @MockBean
    private Navigation navigation;

    @Test
    public void shouldAddNavigationInfoToRequestObject() throws Exception {
        String testRequestURI = "/test-path";

        List<NavigationLink> expectedNavigationLinks = new ArrayList<>();
        expectedNavigationLinks.add(new NavigationLink("http://localhost/test-path", "Test label two"));
        expectedNavigationLinks.add(new NavigationLink("http://localhost/", "Test label three"));

        given(navigation.getNavigationLinks(argThat(request -> request.getRequestURI().equals(testRequestURI))))
                .willReturn(expectedNavigationLinks);

        mockMvc.perform(get(testRequestURI))
                .andExpect(status().isOk())
                .andExpect(model().attribute("navigation", equalTo(expectedNavigationLinks)));
    }


    @TestComponent
    @RequestMapping(method = RequestMethod.GET)
    public static class StubController {


        @WithNavigationLinks
        @RequestMapping("/test-path")
        public String test() {
            return "ignored";
        }
    }
}

