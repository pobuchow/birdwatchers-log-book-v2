package com.blb.main.controller;

import com.blb.main.service.PasswordEncoderService;
import com.blb.main.service.UserAuthenticationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = {BasicAuthController.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BasicAuthControllerTest {

    private static final String BASIC_AUTH_PATH = "/auth";

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private Filter springSecurityFilterChain;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserAuthenticationService userAuthenticationService;

    @MockBean
    private PasswordEncoderService passwordEncoderService;

    @MockBean
    private DaoAuthenticationProvider daoAuthenticationProvider;

    @Test
    @DisplayName("Should return HttpCode 200 when user is authenticated")
    @WithMockUser
    void authenticate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(BASIC_AUTH_PATH + "/basic"))
                .andExpect(status().isOk());
    }
}