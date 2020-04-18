package com.blb.main.service;

import com.blb.main.dao.UserRepository;
import com.blb.main.dto.UserTO;
import com.blb.main.entity.User;
import com.blb.main.service.exception.UserCreationException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {UserService.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    private final static String CORRECT_USERNAME = "USER_A";
    private final static String TOO_SHORT_USERNAME = "ABC";
    private final static String TOO_LONG_USERNAME = "TOO_LONG_USERNAME";

    private final static String CORRECT_EMAIL = "user@qwe.abc";
    private final static String INCORRECT_EMAIL = "user.qwe.abc";

    @BeforeAll
    void mock() throws UserCreationException {
        Mockito.doReturn(new User(new UserTO(1L, CORRECT_USERNAME, CORRECT_EMAIL))).when(userRepository).save(Mockito.any(User.class));
    }

    @Test
    @DisplayName("Should insert new user with correct login and email")
    void insertUserWithCorrectLoginAndEmail() throws UserCreationException {
        UserTO createdUser = userService.insertUser(CORRECT_USERNAME, CORRECT_EMAIL);
        Assertions.assertNotNull(createdUser, "Created user should be not null");
        Assertions.assertEquals(CORRECT_USERNAME, createdUser.getUserName(), "Username: " + CORRECT_USERNAME);
        Assertions.assertEquals(CORRECT_EMAIL, createdUser.getEmail(), "Email: : " + CORRECT_EMAIL);
    }

    @Test
    @DisplayName("Should not insert new user with wrong email")
    void shouldThrowExceptionWhenEmailIsWrong() {
        Assertions.assertThrows(UserCreationException.class, () -> userService.insertUser(CORRECT_USERNAME, INCORRECT_EMAIL), "Email should match pattern" + ".+@.+\\..+");
    }

    @Test
    @DisplayName("Should not insert new user with too short name")
    void shouldThrowExceptionWhenUserNameIsTooShort() {
        Assertions.assertThrows(UserCreationException.class, () -> userService.insertUser(TOO_SHORT_USERNAME, CORRECT_EMAIL), "User name should be at least 5 characters long");
    }

    @Test
    @DisplayName("Should not insert new user with too long name")
    void shouldThrowExceptionWhenUserNameIsTooLong() {
        Assertions.assertThrows(UserCreationException.class, () -> userService.insertUser(TOO_LONG_USERNAME, CORRECT_EMAIL), "User name should be at most 12 characters long");
    }
}