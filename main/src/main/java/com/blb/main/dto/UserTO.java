package com.blb.main.dto;

import com.blb.main.entity.User;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class UserTO {

    private long id;
    private String username;
    private String email;

    public UserTO(User entity) {
        this.id = entity.getId();
        this.username = entity.getUsername();
        this.email = entity.getEmail();
    }
}
