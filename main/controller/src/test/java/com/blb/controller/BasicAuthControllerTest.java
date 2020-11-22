package com.blb.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = BasicAuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class BasicAuthControllerTest {

    private static final String BASIC_AUTH_PATH = "/auth";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BasicAuthController basicAuthController;

    @Test
    @DisplayName("Should return HttpCode 200 when user is authenticated")
    void authenticate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(BASIC_AUTH_PATH + "/basic"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());;
    }

}