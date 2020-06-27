package com.blb.main.dto;

import com.blb.main.entity.User;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class UserTO {

    private long id;
    private String username;
    private String password;
    private String email;

    public UserTO(User entity) {
        this.id = entity.getId();
        this.username = entity.getUserName();
        this.password = entity.getPassword();
        this.email = entity.getEmail();
    }

    public UserTO(long id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() { return password; }
}
