package com.blb.main.controller;

import com.blb.main.dao.UserRepository;
import com.blb.main.dto.UserTO;
import com.blb.main.service.UserService;
import com.blb.main.service.exception.UserCreationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = {UserController.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    private static final String BASIC_USER_PATH = "/user";
    private static final String ADD_USER_PATH = "/add";

    private final static String CORRECT_USERNAME = "USER_A";
    private final static String TOO_SHORT_USERNAME = "ABC";
    private final static String TOO_LONG_USERNAME = "TOO_LONG_USERNAME";

    private final static String CORRECT_EMAIL = "user@qwe.abc";
    private final static String INCORRECT_EMAIL = "user.qwe.abc";

    @Test
    @DisplayName("Should insert new user with correct user name and email and then return its TO")
    void addNewUser() throws UserCreationException, Exception {

        Mockito.doReturn(new UserTO(1L, CORRECT_USERNAME, CORRECT_EMAIL)).when(userService).insertUser(CORRECT_USERNAME, CORRECT_EMAIL);

        mockMvc.perform(MockMvcRequestBuilders
                .post(BASIC_USER_PATH + ADD_USER_PATH)
                .param("username", CORRECT_USERNAME)
                .param("email", CORRECT_EMAIL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.userName", is(CORRECT_USERNAME)).isString())
                .andExpect(jsonPath("$.email", is(CORRECT_EMAIL)).isString())
                .andExpect(jsonPath("$.id").isNumber());
    }

    @Test
    @DisplayName("Should not insert new user with too short user name and then return Exception")
    void addNewUserWithTooShortUserName() throws Exception, UserCreationException {

        Mockito.doThrow(UserCreationException.class).when(userService).insertUser(TOO_SHORT_USERNAME, CORRECT_EMAIL);

        mockMvc.perform(MockMvcRequestBuilders
                .post(BASIC_USER_PATH + ADD_USER_PATH)
                .param("username", TOO_SHORT_USERNAME)
                .param("email", CORRECT_EMAIL))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @DisplayName("Should not insert new user with too long user name and then return Exception")
    void addNewUserWithTooLongUserName() throws Exception, UserCreationException {

        Mockito.doThrow(UserCreationException.class).when(userService).insertUser(TOO_LONG_USERNAME, CORRECT_EMAIL);

        mockMvc.perform(MockMvcRequestBuilders
                .post(BASIC_USER_PATH + ADD_USER_PATH)
                .param("username", TOO_LONG_USERNAME)
                .param("email", CORRECT_EMAIL))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @DisplayName("Should not insert new user with wrong email and then return Exception")
    void addNewUserWithWrongEmail() throws Exception, UserCreationException {

        Mockito.doThrow(UserCreationException.class).when(userService).insertUser(CORRECT_USERNAME, INCORRECT_EMAIL);

        mockMvc.perform(MockMvcRequestBuilders
                .post(BASIC_USER_PATH + ADD_USER_PATH)
                .param("username", CORRECT_USERNAME)
                .param("email", INCORRECT_EMAIL))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @DisplayName("Should not insert new user without user name and then return Exception")
    void addNewUserWithoutUserName() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .post(BASIC_USER_PATH + ADD_USER_PATH)
                .param("email", INCORRECT_EMAIL))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should not insert new user without email and then return Exception")
    void addNewUserWithoutEmail() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .post(BASIC_USER_PATH + ADD_USER_PATH)
                .param("username", CORRECT_USERNAME))
                .andExpect(status().isBadRequest());
    }
}