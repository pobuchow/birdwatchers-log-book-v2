package com.blb.main.dto;

public class LoginCredentialsTO {
    private String username;
    private String password;

    public LoginCredentialsTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
