package com.blb.main.entity;

import com.blb.main.entity.exception.EmailValidationFailedException;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.regex.Pattern;

@Entity
public class Email {

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile(".+@.+\\..+");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotNull
    @Column(name = "email")
    private String email;

    @OneToOne(mappedBy = "email")
    private User user;

    public Email(String email) throws EmailValidationFailedException {
        validate(email);
        this.email = email;
    }

    public Email() {
        //constructor for jpa
    }

    private void validate(String email) throws EmailValidationFailedException {
        if (email == null) throw new EmailValidationFailedException("Email can not be null");
        if (!VALID_EMAIL_ADDRESS_REGEX.matcher(email).matches())
            throw new EmailValidationFailedException(email + " does not match " + VALID_EMAIL_ADDRESS_REGEX + " pattern.");
    }

    public String getEmail() {
        return email;
    }
}
