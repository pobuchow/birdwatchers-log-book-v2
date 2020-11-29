package com.blb.service;

import com.blb.dto.UserTO;
import com.blb.entity.User;
import com.blb.entity.exception.EmailValidationFailedException;
import com.blb.entity.exception.LoginValidationFailedException;
import com.blb.repository.UserRepository;
import com.blb.service.exception.UserCreationException;
import com.blb.service.exception.UserNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoderService passwordEncoderService;

    private static final Logger logger = LogManager.getLogger(UserService.class);

    private static final int MIN_USERNAME_LENGTH = 5;
    private static final int MAX_USERNAME_LENGTH = 12;
    private static final int MIN_PASS_LENGTH = 8;
    private static final int MAX_PASS_LENGTH = 100;

    private static final Pattern HAS_UPPERCASE = Pattern.compile("[A-Z]");
    private static final Pattern HAS_LOWERCASE = Pattern.compile("[a-z]");
    private static final Pattern HAS_NUMBER = Pattern.compile("\\d");
    private static final Pattern HAS_SPECIAL_CHAR = Pattern.compile("[^a-zA-Z0-9]");

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile(".+@.+\\..+");

    public UserTO insertUser(String userName, String password, String email) throws UserCreationException {
        final User userToSave;
        try {
            userToSave = new User(validateUsername(userName),
                    passwordEncoderService.passwordEncoder().encode(validatePassword(password)),
                    validateEmail(email));
        } catch (LoginValidationFailedException | EmailValidationFailedException e) {
            throw new UserCreationException(e.getMessage());
        }
        User savedUser;
        try {
            savedUser = userRepository.save(userToSave);
        }catch (ConstraintViolationException ex){
            throw new UserCreationException(ex.getMessage());
        }
        return new UserTO(
                savedUser.getId(),
                savedUser.getUserName(),
                savedUser.getEmail(),
                savedUser.getPassword(),
                new ArrayList<>());
    }

    Long getAuthenticatedUserId() throws UserNotFoundException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        final Long id = userRepository.findByLoginUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username))
                .getId();
        if(Objects.isNull(id)){
            logger.error(MessageFormat.format("Corrupted entity - User with the name: {0} has no id", username));
            throw new UserNotFoundException(username);
        }
        return id;
    }

    Optional<User> findByUsername(String username) {
        return userRepository.findByLoginUsername(username);
    }

    private String validatePassword(String password) throws LoginValidationFailedException {
        StringBuilder errorMessage = new StringBuilder();
        if ( password.length() > MAX_PASS_LENGTH)
            throw new LoginValidationFailedException(MessageFormat.format("Password should contain not more then {0} characters", MAX_PASS_LENGTH));
        if ( password.length() < MIN_PASS_LENGTH)
            throw new LoginValidationFailedException(MessageFormat.format("Password should contain at least {0} characters", MIN_PASS_LENGTH));
        if (!HAS_UPPERCASE.matcher(password).find()){
            errorMessage.append("Password should contain a upper case\n");
        }
        if (!HAS_LOWERCASE.matcher(password).find()){
            errorMessage.append("Passord should contain a lower case\n");
        }
        if (!HAS_SPECIAL_CHAR.matcher(password).find()){
            errorMessage.append("Password should contain a special character\n");
        }
        if (!HAS_NUMBER.matcher(password).find()){
            errorMessage.append("Password should contain a number\n");
        }
        if(errorMessage.length() != 0) throw new LoginValidationFailedException(errorMessage.toString());
        return password;
    }

    private String validateUsername(String username) throws LoginValidationFailedException {
        if ( username.length() > MAX_USERNAME_LENGTH)
            throw new LoginValidationFailedException(MessageFormat.format("Username should contain not more then {0} characters", MAX_USERNAME_LENGTH));
        if ( username.length() < MIN_USERNAME_LENGTH)
            throw new LoginValidationFailedException(MessageFormat.format("Username should contain at least {0} characters", MIN_USERNAME_LENGTH));
        if(userRepository.findByLoginUsername(username).isPresent())
            throw new LoginValidationFailedException("Username must be unique");
        return username;
    }

    private String validateEmail(String email) throws EmailValidationFailedException {
        if (email == null) throw new EmailValidationFailedException("Email can not be null");
        if (!VALID_EMAIL_ADDRESS_REGEX.matcher(email).matches())
            throw new EmailValidationFailedException(MessageFormat.format("{0} does not match {1} pattern.", email, VALID_EMAIL_ADDRESS_REGEX));
        return email;
    }
}
