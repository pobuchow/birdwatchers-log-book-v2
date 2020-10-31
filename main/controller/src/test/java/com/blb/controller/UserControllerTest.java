package com.blb.controller;

import com.blb.dto.UserTO;
import com.blb.service.PasswordEncoderService;
import com.blb.service.UserAuthenticationService;
import com.blb.service.UserService;
import com.blb.service.exception.UserCreationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = {UserController.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserAuthenticationService userAuthenticationService;

    @MockBean
    private PasswordEncoderService passwordEncoderService;

    @MockBean
    private DaoAuthenticationProvider daoAuthenticationProvider;

    private final static String BASIC_USER_PATH = "/users";
    private final static String CREATE_USER_PATH = "/create";

    private final static String CORRECT_USERNAME = "USER_A";
    private final static String TOO_LONG_USERNAME = "TOO_LONG_USERNAME";

    private final static String CORRECT_EMAIL = "user@qwe.abc";
    private final static String INCORRECT_EMAIL = "user.qwe.abc";

    private final static String CORRECT_PASSWORD = "C0rrect!";

    @Test
    @DisplayName("Should insert new user with correct user name, password and email and then return its TO")
    void addNewUser() throws Exception {

        Mockito.doReturn(new UserTO(1L, CORRECT_USERNAME, CORRECT_EMAIL, CORRECT_PASSWORD, new ArrayList<>())).when(userService).insertUser(CORRECT_USERNAME, CORRECT_PASSWORD, CORRECT_EMAIL);

        mockMvc.perform(MockMvcRequestBuilders
                .post(BASIC_USER_PATH + CREATE_USER_PATH)
                .param("username", CORRECT_USERNAME)
                .param("password", CORRECT_PASSWORD)
                .param("email", CORRECT_EMAIL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.username", is(CORRECT_USERNAME)).isString())
                .andExpect(jsonPath("$.password", is(CORRECT_PASSWORD)).isString())
                .andExpect(jsonPath("$.email", is(CORRECT_EMAIL)).isString())
                .andExpect(jsonPath("$.id").isNumber());
    }

    @Test
    @DisplayName("Should not insert new user with too long user name and then return Exception")
    void addNewUserWithWrongUserName() throws Exception, UserCreationException {

        Mockito.doThrow(UserCreationException.class).when(userService).insertUser(TOO_LONG_USERNAME, CORRECT_PASSWORD, CORRECT_EMAIL);

        mockMvc.perform(MockMvcRequestBuilders
                .post(BASIC_USER_PATH + CREATE_USER_PATH)
                .param("username", TOO_LONG_USERNAME)
                .param("password", CORRECT_PASSWORD)
                .param("email", CORRECT_EMAIL))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @DisplayName("Should not insert new user with wrong email and then return Exception")
    void addNewUserWithWrongEmail() throws Exception, UserCreationException {

        Mockito.doThrow(UserCreationException.class).when(userService).insertUser(CORRECT_USERNAME, CORRECT_PASSWORD, INCORRECT_EMAIL);

        mockMvc.perform(MockMvcRequestBuilders
                .post(BASIC_USER_PATH + CREATE_USER_PATH)
                .param("username", CORRECT_USERNAME)
                .param("password", CORRECT_PASSWORD)
                .param("email", INCORRECT_EMAIL))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @DisplayName("Should not insert new user without user name and then return Exception")
    void addNewUserWithoutUserName() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .post(BASIC_USER_PATH + CREATE_USER_PATH)
                .param("password", CORRECT_PASSWORD)
                .param("email", CORRECT_EMAIL))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should not insert new user without email and then return Exception")
    void addNewUserWithoutEmail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post(BASIC_USER_PATH + CREATE_USER_PATH)
                .param("username", CORRECT_USERNAME)
                .param("password", CORRECT_PASSWORD))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should not insert new user without password and then return Exception")
    void addNewUserWithoutPassword() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post(BASIC_USER_PATH + CREATE_USER_PATH)
                .param("username", CORRECT_USERNAME)
                .param("email", CORRECT_EMAIL))
                .andExpect(status().isBadRequest());
    }
}