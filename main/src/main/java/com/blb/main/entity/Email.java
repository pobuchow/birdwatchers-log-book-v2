package com.blb.main.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotNull
    @Column(name = "email")
    private String email;

    @OneToOne(mappedBy = "email")
    private User user;

    public Email(String email) {
        this.email = email;
    }

    public Email() {
        //constructor for jpa
    }

    public String getEmail() {
        return email;
    }
}
