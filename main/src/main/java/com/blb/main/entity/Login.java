package com.blb.main.entity;

import com.blb.main.entity.exception.LoginValidationFailedException;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Login {

    private static final int MIN_LOGIN_LENGTH = 5;
    private static final int MAX_LOGIN_LENGTH = 12;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @NotNull
    @Size(min = MIN_LOGIN_LENGTH, max = MAX_LOGIN_LENGTH)
    @Column(name = "login")
    String login;

    @OneToOne(mappedBy = "login")
    private User user;

    public Login() {
    }

    public Login(String login) throws LoginValidationFailedException {
        validate(login);
        this.login = login;
    }

    private void validate(String str) throws LoginValidationFailedException {
        if (str == null)
            throw new LoginValidationFailedException("Nick can not be null");
        if ( str.length() > MAX_LOGIN_LENGTH )
            throw new LoginValidationFailedException("Nick should contain not more then " + MAX_LOGIN_LENGTH + "characters");
        if ( str.length() < MIN_LOGIN_LENGTH )
            throw new LoginValidationFailedException("Nick should contain at least " + MIN_LOGIN_LENGTH + "characters");
    }

    public String getLogin() {
        return login;
    }
}
