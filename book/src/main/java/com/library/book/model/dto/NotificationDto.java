package com.library.book.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NotificationDto {

    private String title;

    private Double rating;

    private String firstName;

    private String email;
}
