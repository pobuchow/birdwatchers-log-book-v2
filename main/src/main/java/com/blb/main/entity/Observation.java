package com.blb.main.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
public class Observation {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    private String speciesName;

    @NotNull
    private LocalDate date;

    @ManyToOne
    @NotNull
    private User user;

    public Observation() {
        // empty constructor for JPA
    }

    public Observation(String speciesName, LocalDate date, User user) {
        this.speciesName = speciesName;
        this.date = date;
        this.user = user;
    }

    public Observation(String speciesName, LocalDate date) {
    }

    public long getId() {
        return id;
    }

    public String getSpeciesName() {
        return speciesName;
    }

    public LocalDate getDate() {
        return date;
    }

    public User getUser() {
        return user;
    }
}
