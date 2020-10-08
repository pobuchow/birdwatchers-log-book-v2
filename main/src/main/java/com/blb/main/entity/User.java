package com.blb.main.entity;

import com.blb.main.dto.UserTO;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    public User(String login, String password, String email) {
        this.login = new Login(login, password);
        this.email = new Email(email);
    }

    public User(UserTO userTO) {
        this(userTO.getUsername(), userTO.getPassword(), userTO.getEmail());
    }

    public User() {
        //constructor jor jpa
    }

    public Long getId() {
        return id;
    }

    public Login getLogin() {
        return login;
    }

    public String getUserName() {
        return login.getUsername();
    }

    public String getEmail() {
        return email.getEmail();
    }

    public String getPassword() {
        return login.getPassword();
    }

    public void setId(Long id) {
        this.id = id;
    }
}
