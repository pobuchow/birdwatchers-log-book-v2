package com.blb.main.entity;

import com.blb.main.entity.exception.EmailValidationFailedException;
import com.blb.main.entity.exception.LoginValidationFailedException;
import com.blb.main.entity.exception.UserCreationException;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class User {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "login_id", referencedColumnName = "id")
    private Login login;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "email_id", referencedColumnName = "id")
    private Email email;

    public User() {
    }

    public User(@NotNull Login login, @NotNull Email email) {
        this.login = login;
        this.email = email;
    }

    public User(String login, String email) throws UserCreationException {
        try {
            this.login = new Login(login);
        } catch (LoginValidationFailedException e) {
            throw new UserCreationException("Error while creating login: " + e.getMessage());
        }
        try {
            this.email = new Email(email);
        } catch (EmailValidationFailedException e) {
            throw new UserCreationException("Error while creating email: " + e.getMessage());
        }
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return login.getLogin();
    }

    public String getEmail() {
        return email.getEmail();
    }
}
