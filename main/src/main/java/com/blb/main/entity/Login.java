package com.blb.main.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.regex.Pattern;

@Entity
public class Login {

    private static final int MIN_USERNAME_LENGTH = 5;
    private static final int MAX_USERNAME_LENGTH = 12;
    private static final int MIN_PASS_LENGTH = 8;
    private static final int MAX_PASS_LENGTH = 100;

    private static final Pattern HAS_UPPERCASE = Pattern.compile("[A-Z]");
    private static final Pattern HAS_LOWERCASE = Pattern.compile("[a-z]");
    private static final Pattern HAS_NUMBER = Pattern.compile("\\d");
    private static final Pattern HAS_SPECIAL_CHAR = Pattern.compile("[^a-zA-Z0-9]");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotNull
    @Size(min = MIN_USERNAME_LENGTH, max = MAX_USERNAME_LENGTH)
    @Column(name = "username")
    private String userName;

    @NotNull
    @Column(name = "password")
    private String password;

    @OneToOne(mappedBy = "login")
    private User user;

    Login(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public Login() {
        //constructor for jpa
    }

    public String getUsername() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
