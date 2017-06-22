package com.ihordev.web;


import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.util.Locale;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@WebMvcTest(controllers = MainController.class)
public class MainControllerTests extends AbstractMockMvcTests {

    @Test
    public void shouldSuccessfullyRenderViewMain() throws Exception {
        mockMvc.perform(get("/").locale(Locale.ENGLISH))
                .andExpect(status().isOk())
                .andExpect(view().name("greetings"));
    }
}