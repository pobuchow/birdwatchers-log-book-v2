package com.blb.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Login {

    private static final int MIN_USERNAME_LENGTH = 5;
    private static final int MAX_USERNAME_LENGTH = 12;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = MIN_USERNAME_LENGTH, max = MAX_USERNAME_LENGTH)
    @Column(name = "username")
    private String username;

    @NotNull
    @Column(name = "password")
    private String password;

    @OneToOne(mappedBy = "login")
    private User user;

    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Login() {
        //constructor for jpa
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}