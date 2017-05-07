package com.ihordev.web;

import com.ihordev.service.ExampleService;
import org.junit.Test;

import org.springframework.boot.test.mock.mockito.MockBean;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


public class MainControllerTests extends AbstractMockMvcTests {

    @MockBean
    private ExampleService exampleService;


    @Test
    public void shouldReturnTestString() throws Exception {
        mockMvc.perform(get("/test"))
                .andExpect(status().isOk())
                .andExpect(view().name("main"));
    }

}