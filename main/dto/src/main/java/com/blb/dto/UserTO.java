package com.blb.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;


import java.util.ArrayList;
import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class UserTO {

    private Long id;
    private String username;
    private String password;
    private String email;
    private List<ObservationTO> observations;

    public UserTO(Long id, String username, String email, String password, List<ObservationTO> observations) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.observations = new ArrayList<>(observations);
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() { return password; }

    public List<ObservationTO> getObservations() {
        return observations;
    }
}
