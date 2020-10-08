package com.blb.main.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
public class Observation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    public Observation(Long id, String speciesName, LocalDate date, User user) {
        this.id = id;
        this.speciesName = speciesName;
        this.date = date;
        this.user = user;
    }

    public Observation(String speciesName, LocalDate date) {
    }

    public Long getId() {
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
