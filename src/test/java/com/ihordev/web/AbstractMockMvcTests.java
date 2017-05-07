package com.ihordev.web;

import com.ihordev.config.AppProfiles;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


@RunWith(SpringRunner.class)
@WebMvcTest
@ActiveProfiles(AppProfiles.WEB_TESTS)
public class AbstractMockMvcTests {

    @Autowired
    protected MockMvc mockMvc;

}
