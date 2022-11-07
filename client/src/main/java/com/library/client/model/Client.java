package com.library.client.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long clientId;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotNull
    @Email
    @Column(unique = true)
    private String email;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        Active,
        Inactive
    }

}
