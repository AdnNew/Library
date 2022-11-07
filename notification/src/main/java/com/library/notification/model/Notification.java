package com.library.notification.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Notification {

    private String title;

    private Double rating;

    private String firstName;

    private String email;
}
