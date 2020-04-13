package com.blb.main.dto;

import com.blb.main.entity.User;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class UserTO {

    private long id;
    private String userName;
    private String email;

    public UserTO(User entity) {
        this.id = entity.getId();
        this.userName = entity.getUserName();
        this.email = entity.getEmail();
    }

    public UserTO(long id, String username, String email) {
        this.id = id;
        this.userName = username;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }
}
