package com.blb.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.time.LocalDate;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ObservationTO {

    private Long id;
    private String speciesName;
    private LocalDate date;
    private String username;

    public ObservationTO(Long id, String speciesName, LocalDate date, String username) {
        this.id = id;
        this.speciesName = speciesName;
        this.date = date;
        this.username = username;
    }

    public String getSpeciesName() {
        return speciesName;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getUsername() {
        return username;
    }
}
