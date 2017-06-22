package com.ihordev.web;

import com.ihordev.config.AppProfiles;
import info.solidsoft.mockito.java8.api.WithBDDMockito;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.core.Ordered;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.Map;



@RunWith(SpringRunner.class)
@ActiveProfiles(AppProfiles.WEB_TESTS)
public abstract class AbstractMockMvcTests implements WithBDDMockito {

    @Autowired
    protected MockMvc mockMvc;


    @TestComponent
    public static class StubViewResolver implements ViewResolver, Ordered {

        @Override
        public View resolveViewName(String viewName, Locale locale) throws Exception {
            return new StubView();
        }

        @Override
        public int getOrder() {
            return 0;
        }
    }

    public static class StubView implements View {

        @Override
        public String getContentType() {
            return "text/html";
        }

        @Override
        public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
            response.getWriter().println("Stub view generated content.");
            response.getWriter().flush();
        }
    }
}
