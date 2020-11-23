package com.blb.service;

import com.blb.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {UserAuthenticationService.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserAuthenticationServiceTest {

    private final static String CORRECT_USERNAME = "USER_A";
    private final static String CORRECT_EMAIL = "user@qwe.abc";
    private final static String CORRECT_PASSWORD = "C0rrect!";
    private final static String NOT_EXISTING_USERNAME = "USER_A-1";

    @Autowired
    private UserAuthenticationService userAuthenticationService;
    @MockBean
    private UserService userService;

    @Test
    @DisplayName("Should load user by its name")
    void loadUserByUsername() {
        Mockito.doReturn(
                Optional.of(
                        new User(
                                CORRECT_USERNAME,
                                CORRECT_PASSWORD,
                                CORRECT_EMAIL)))
                .when(userService).findByUsername(CORRECT_USERNAME);

        UserDetails result = userAuthenticationService.loadUserByUsername(CORRECT_USERNAME);
        Assertions.assertEquals(CORRECT_USERNAME, result.getUsername());
        Assertions.assertEquals(CORRECT_PASSWORD, result.getPassword());
    }

    @Test
    @DisplayName("Should throw com.blb.entity.exception when user not found")
    void loadNotExistingUserByUsername() {
        Mockito.doReturn(
                Optional.empty())
                .when(userService).findByUsername(NOT_EXISTING_USERNAME);
        Assertions.assertThrows(UsernameNotFoundException.class,
                () -> userAuthenticationService.loadUserByUsername(NOT_EXISTING_USERNAME));
    }
}