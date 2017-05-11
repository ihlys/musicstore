package com.ihordev.web;

import com.ihordev.config.AppProfiles;
import info.solidsoft.mockito.java8.api.WithBDDMockito;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;



@RunWith(SpringRunner.class)
@ActiveProfiles(AppProfiles.WEB_TESTS)
public abstract class AbstractMockMvcTests implements WithBDDMockito {

    @Autowired
    protected MockMvc mockMvc;

    @TestConfiguration
    @ComponentScan(excludeFilters = { @ComponentScan.Filter(
            type = FilterType.REGEX,
            pattern = "^(?!.*com.ihordev.web.config.+).*$")
    })
    public static class TestWebConfig {}
}
