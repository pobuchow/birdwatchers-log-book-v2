package com.blb.main.entity;

import com.blb.main.entity.exception.LoginValidationFailedException;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Login {

    private static final int MIN_USERNAME_LENGTH = 5;
    private static final int MAX_USERNAME_LENGTH = 12;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @NotNull
    @Size(min = MIN_USERNAME_LENGTH, max = MAX_USERNAME_LENGTH)
    @Column(name = "userName")
    String userName;

    @OneToOne(mappedBy = "login")
    private User user;

    public Login(String userName) throws LoginValidationFailedException {
        validate(userName);
        this.userName = userName;
    }

    private void validate(String str) throws LoginValidationFailedException {
        if (str == null)
            throw new LoginValidationFailedException("Nick can not be null");
        if ( str.length() > MAX_USERNAME_LENGTH)
            throw new LoginValidationFailedException("Nick should contain not more then " + MAX_USERNAME_LENGTH + "characters");
        if ( str.length() < MIN_USERNAME_LENGTH)
            throw new LoginValidationFailedException("Nick should contain at least " + MIN_USERNAME_LENGTH + "characters");
    }

    public String getUserName() {
        return userName;
    }
}
