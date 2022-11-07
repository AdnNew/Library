package com.library.book.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Borrower {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime borrowedTime;

    private String email;

    private String firstName;

    private String lastName;

    public Borrower(String email, String firstName, String lastName) {
        this.borrowedTime = LocalDateTime.now();
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
