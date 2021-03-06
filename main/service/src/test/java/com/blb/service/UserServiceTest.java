package com.blb.service;

import com.blb.dto.UserTO;
import com.blb.entity.User;
import com.blb.repository.UserRepository;
import com.blb.service.exception.UserCreationException;
import com.blb.service.exception.UserNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {UserService.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceTest {

    private final static String CORRECT_USERNAME = "USER_A";
    private final static String NOT_EXISTING_USERNAME = "USER_A-1";
    private final static String TOO_SHORT_USERNAME = "ABC";
    private final static String TOO_LONG_USERNAME = "TOO_LONG_USERNAME";
    private final static String CORRECT_EMAIL = "user@qwe.abc";
    private final static String INCORRECT_EMAIL = "user.qwe.abc";
    private final static String CORRECT_PASSWORD = "C0rrect!";
    private final static String WRONG_PASSWORD = "C0rrect!123";
    private static final String TOO_LONG_PASSWORD = "PASSWORD01#TooLong";
    private static final String TOO_SHORT_PASSWORD = "Pas0#";
    private static final String PASSWORD_WITHOUT_NUMBER = "Password!";
    private static final String PASSWORD_WITHOUT_UPPER_CASE = "password01#";
    private static final String PASSWORD_WITHOUT_LOWER_CASE = "PASSWORD01#";
    private static final String PASSWORD_WITHOUT_SPECIAL_CHARACTER = "Password01";
    @Autowired
    private UserService userService;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private PasswordEncoderService passwordEncoderService;

    @Test
    @DisplayName("Should insert new user with correct login, password and email")
    void insertUserWithCorrectLoginAndEmail() throws UserCreationException {
        Mockito.doReturn(new User(CORRECT_USERNAME, CORRECT_PASSWORD, CORRECT_EMAIL))
                .when(userRepository).save(Mockito.any(User.class));
        PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
        Mockito.doReturn(passwordEncoder).when(passwordEncoderService).passwordEncoder();
        Mockito.doReturn(CORRECT_PASSWORD).when(passwordEncoder).encode(CORRECT_PASSWORD);

        UserTO createdUser = userService.insertUser(CORRECT_USERNAME, CORRECT_PASSWORD, CORRECT_EMAIL);
        Assertions.assertNotNull(createdUser, "Created user should be not null");
        Assertions.assertEquals(CORRECT_USERNAME, createdUser.getUsername(), "Username: " + CORRECT_USERNAME);
        Assertions.assertEquals(CORRECT_PASSWORD, createdUser.getPassword(), "Password: " + CORRECT_PASSWORD);
        Assertions.assertEquals(CORRECT_EMAIL, createdUser.getEmail(), "Email: : " + CORRECT_EMAIL);
    }

    @Test
    @DisplayName("Should not insert new user with wrong email")
    void shouldThrowExceptionWhenEmailIsWrong() {
        PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
        Mockito.doReturn(passwordEncoder).when(passwordEncoderService).passwordEncoder();
        Mockito.doReturn(CORRECT_PASSWORD).when(passwordEncoder).encode(CORRECT_PASSWORD);
        Assertions.assertThrows(UserCreationException.class,
                () -> userService.insertUser(CORRECT_USERNAME, CORRECT_PASSWORD, INCORRECT_EMAIL),
                "Email should match pattern" + ".+@.+\\..+");
    }

    @Test
    @DisplayName("Should not insert new user with too short name")
    void shouldThrowExceptionWhenUserNameIsTooShort() {
        Assertions.assertThrows(UserCreationException.class,
                () -> userService.insertUser(TOO_SHORT_USERNAME, CORRECT_PASSWORD, CORRECT_EMAIL),
                "User name should be at least 5 characters long");
    }

    @Test
    @DisplayName("Should not insert new user with too long name")
    void shouldThrowExceptionWhenUserNameIsTooLong() {
        Assertions.assertThrows(UserCreationException.class,
                () -> userService.insertUser(TOO_LONG_USERNAME, CORRECT_PASSWORD, CORRECT_EMAIL),
                "User name should be at most 12 characters long");
    }

    @Test
    @DisplayName("Should not insert new user with too short password")
    void shouldThrowExceptionWhenPasswordIsTooShort() {
        Assertions.assertThrows(UserCreationException.class,
                () -> userService.insertUser(CORRECT_USERNAME, TOO_SHORT_PASSWORD, CORRECT_EMAIL),
                "Password should be at least 8 characters long");
    }

    @Test
    @DisplayName("Should not insert new user with no number in password")
    void shouldThrowExceptionWhenPasswordHasNoNumber() {
        Assertions.assertThrows(UserCreationException.class,
                () -> userService.insertUser(CORRECT_USERNAME, PASSWORD_WITHOUT_NUMBER, CORRECT_EMAIL),
                "Password should contain a number");
    }

    @Test
    @DisplayName("Should not insert new user with no upper case in password")
    void shouldThrowExceptionWhenPasswordHasNoUpperCase() {
        Assertions.assertThrows(UserCreationException.class,
                () -> userService.insertUser(CORRECT_USERNAME, PASSWORD_WITHOUT_UPPER_CASE, CORRECT_EMAIL),
                "Password should contain a upper case");
    }

    @Test
    @DisplayName("Should not insert new user with no lower case in password")
    void shouldThrowExceptionWhenPasswordHasNoLowerCase() {
        Assertions.assertThrows(UserCreationException.class,
                () -> userService.insertUser(CORRECT_USERNAME, PASSWORD_WITHOUT_LOWER_CASE, CORRECT_EMAIL),
                "Password should contain a lower case");
    }

    @Test
    @DisplayName("Should not insert new user with no special character in password")
    void shouldThrowExceptionWhenPasswordHasNoSpecialCharacter() {
        Assertions.assertThrows(UserCreationException.class,
                () -> userService.insertUser(CORRECT_USERNAME, PASSWORD_WITHOUT_SPECIAL_CHARACTER, CORRECT_EMAIL),
                "Password should contain a special character");
    }

    @Test
    @DisplayName("Should not insert new user with not unique username")
    void shouldThrowExceptionWhenUserNameIsNotUnique() {
        Mockito.when(userRepository.findByLoginUsername(CORRECT_USERNAME)).thenReturn(Optional.of(new User()));
        Assertions.assertThrows(UserCreationException.class,
                () -> userService.insertUser(CORRECT_USERNAME, CORRECT_PASSWORD, CORRECT_EMAIL),
                "UserName must be unique");
    }

    @Test
    @DisplayName("Should return id of authorized user")
    void getAuthenticatedUserId() throws UserNotFoundException {
        User user = new User();
        final Long expectedId = 1L;
        user.setId(expectedId);
        Authentication mockedAuthentication = Mockito.mock(Authentication.class);
        SecurityContext mockedContext = Mockito.mock(SecurityContext.class);
        Mockito.when(mockedContext.getAuthentication()).thenReturn(mockedAuthentication);
        SecurityContextHolder.setContext(mockedContext);
        Mockito.doReturn(CORRECT_USERNAME).when(mockedAuthentication).getName();
        Mockito.doReturn(Optional.of(user)).when(userRepository).findByLoginUsername(CORRECT_USERNAME);
        Assertions.assertEquals(expectedId, userService.getAuthenticatedUserId());
    }

    @Test
    @DisplayName("Should throw UserNotFoundException when no authenticated user found")
    void getNotAuthenticatedUserId() throws UserNotFoundException {
        Authentication mockedAuthentication = Mockito.mock(Authentication.class);
        SecurityContext mockedContext = Mockito.mock(SecurityContext.class);
        Mockito.when(mockedContext.getAuthentication()).thenReturn(mockedAuthentication);
        SecurityContextHolder.setContext(mockedContext);
        Mockito.doReturn(null).when(mockedAuthentication).getName();
        Mockito.doReturn(Optional.empty()).when(userRepository).findByLoginUsername(null);
        Assertions.assertThrows(UserNotFoundException.class,
                () -> userService.getAuthenticatedUserId());
    }

    @Test
    @DisplayName("Should throw UserNotFoundException user entity is corrupted")
    void testGetAuthenticatedUserId() {
        User user = new User();
        Authentication mockedAuthentication = Mockito.mock(Authentication.class);
        SecurityContext mockedContext = Mockito.mock(SecurityContext.class);
        Mockito.when(mockedContext.getAuthentication()).thenReturn(mockedAuthentication);
        SecurityContextHolder.setContext(mockedContext);
        Mockito.doReturn(CORRECT_USERNAME).when(mockedAuthentication).getName();
        Mockito.doReturn(Optional.of(user)).when(userRepository).findByLoginUsername(CORRECT_USERNAME);
        Assertions.assertThrows(UserNotFoundException.class,
                () -> userService.getAuthenticatedUserId(),
                "Corrupted entity - User with the name: " + CORRECT_USERNAME + " has no id");
    }
}