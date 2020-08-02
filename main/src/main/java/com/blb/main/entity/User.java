package com.blb.main.entity;

import com.blb.main.dto.UserTO;
import com.blb.main.entity.exception.EmailValidationFailedException;
import com.blb.main.entity.exception.LoginValidationFailedException;
import com.blb.main.service.exception.UserCreationException;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "login_id", referencedColumnName = "id")
    private Login login;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "email_id", referencedColumnName = "id")
    private Email email;

    @OneToMany
    private List<Observation> observations;

    public User(String login, String password, String email) throws UserCreationException {
        try {
            this.login = new Login(login, password);
        } catch (LoginValidationFailedException e) {
            throw new UserCreationException("Error while creating login: " + e.getMessage());
        }
        try {
            this.email = new Email(email);
        } catch (EmailValidationFailedException e) {
            throw new UserCreationException("Error while creating email: " + e.getMessage());
        }
    }

    public User(UserTO userTO) throws UserCreationException {
        this(userTO.getUsername(), userTO.getPassword(), userTO.getEmail());
    }

    public User() {
        //constructor jor jpa
    }

    public long getId() {
        return id;
    }

    public String getUserName() {
        return login.getUserName();
    }

    public String getEmail() {
        return email.getEmail();
    }

    public String getPassword() {
        return login.getPassword();
    }
}
