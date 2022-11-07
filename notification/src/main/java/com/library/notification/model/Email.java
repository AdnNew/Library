package com.library.notification.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
public class Email {

    @NotBlank
    private String to;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
}
