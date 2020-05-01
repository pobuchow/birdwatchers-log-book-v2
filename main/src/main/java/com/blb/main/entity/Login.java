package com.blb.main.entity;

import com.blb.main.entity.exception.LoginValidationFailedException;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.regex.Pattern;

@Entity
public class Login {

    private static final int MIN_USERNAME_LENGTH = 5;
    private static final int MAX_USERNAME_LENGTH = 12;
    private static final int MIN_PASS_LENGTH = 8;
    private static final int MAX_PASS_LENGTH = 16;

    private static final Pattern HAS_UPPERCASE = Pattern.compile("[A-Z]");
    private static final Pattern HAS_LOWERCASE = Pattern.compile("[a-z]");
    private static final Pattern HAS_NUMBER = Pattern.compile("\\d");
    private static final Pattern HAS_SPECIAL_CHAR = Pattern.compile("[^a-zA-Z0-9]");

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @NotNull
    @Size(min = MIN_USERNAME_LENGTH, max = MAX_USERNAME_LENGTH)
    @Column(name = "user_name")
    String userName;

    @NotNull
    @Size(min = MIN_PASS_LENGTH, max = MAX_PASS_LENGTH)
    @Column(name = "password")
    String password;

    @OneToOne(mappedBy = "login")
    private User user;

    public Login(String userName, String password) throws LoginValidationFailedException {
        this.userName = validateUserName(userName);
        this.password = validatePassword(password);
    }

    public Login() {
        //constructor for jpa
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

    private String validateUserName(String str) throws LoginValidationFailedException {
        if ( str.length() > MAX_USERNAME_LENGTH)
            throw new LoginValidationFailedException("Nick should contain not more then " + MAX_USERNAME_LENGTH + " characters");
        if ( str.length() < MIN_USERNAME_LENGTH)
            throw new LoginValidationFailedException("Nick should contain at least " + MIN_USERNAME_LENGTH + " characters");
        return str;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
