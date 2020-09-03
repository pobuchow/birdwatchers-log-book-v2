package com.blb.main.service;

import com.blb.main.dao.UserRepository;
import com.blb.main.dto.LoginCredentialsTO;
import com.blb.main.dto.UserTO;
import com.blb.main.entity.User;
import com.blb.main.entity.exception.EmailValidationFailedException;
import com.blb.main.entity.exception.LoginValidationFailedException;
import com.blb.main.service.exception.UserAuthenticationException;
import com.blb.main.service.exception.UserCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoderService passwordEncoderService;

    private static final int MIN_USERNAME_LENGTH = 5;
    private static final int MAX_USERNAME_LENGTH = 12;
    private static final int MIN_PASS_LENGTH = 8;
    private static final int MAX_PASS_LENGTH = 100;

    private static final Pattern HAS_UPPERCASE = Pattern.compile("[A-Z]");
    private static final Pattern HAS_LOWERCASE = Pattern.compile("[a-z]");
    private static final Pattern HAS_NUMBER = Pattern.compile("\\d");
    private static final Pattern HAS_SPECIAL_CHAR = Pattern.compile("[^a-zA-Z0-9]");

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile(".+@.+\\..+");

    public UserTO insertUser(String userName, String password, String email)
            throws UserCreationException, LoginValidationFailedException, EmailValidationFailedException {
        final User userToSave = new User(validateUsername(userName),
                passwordEncoderService.passwordEncoder().encode(validatePassword(password)),
                validateEmail(email));
        if(userRepository.findByLoginUserName(userName).isPresent())
            throw new UserCreationException("Username must be unique");
        User savedUser;
        try {
            savedUser = userRepository.save(userToSave);
        }catch (ConstraintViolationException ex){
            throw new UserCreationException(ex.getMessage());
        }
        return new UserTO(savedUser);
    }

    public LoginCredentialsTO authorize(String username, String password) throws UserAuthenticationException {
        final User user = userRepository.findByLoginUserName(username)
                .orElseThrow(() -> new UserAuthenticationException("User with the name: " + username + " not found"));
        if(user.getPassword().equals(password)){
            return new LoginCredentialsTO(username, password);
        }else{
            throw new UserAuthenticationException("Password is not correct");
        }
    }

    long getAuthorizedUserId() throws UserAuthenticationException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByLoginUserName(username)
                .orElseThrow(() -> new UserAuthenticationException("User with the name: " + username + " not found"))
                .getId();
    }

    Optional<User> findByUsername(String username) {
        return userRepository.findByLoginUserName(username);
    }

    private String validatePassword(String password) throws LoginValidationFailedException {
        StringBuilder errorMessage = new StringBuilder();
        if ( password.length() > MAX_PASS_LENGTH)
            throw new LoginValidationFailedException("Password should contain not more then " + MAX_PASS_LENGTH + " characters");
        if ( password.length() < MIN_PASS_LENGTH)
            throw new LoginValidationFailedException("Password should contain at least " + MIN_PASS_LENGTH + " characters");
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

    private String validateUsername(String str) throws LoginValidationFailedException {
        if ( str.length() > MAX_USERNAME_LENGTH)
            throw new LoginValidationFailedException("Nick should contain not more then " + MAX_USERNAME_LENGTH + " characters");
        if ( str.length() < MIN_USERNAME_LENGTH)
            throw new LoginValidationFailedException("Nick should contain at least " + MIN_USERNAME_LENGTH + " characters");
        return str;
    }

    private String validateEmail(String email) throws EmailValidationFailedException {
        if (email == null) throw new EmailValidationFailedException("Email can not be null");
        if (!VALID_EMAIL_ADDRESS_REGEX.matcher(email).matches())
            throw new EmailValidationFailedException(email + " does not match " + VALID_EMAIL_ADDRESS_REGEX + " pattern.");
        return email;
    }
}
