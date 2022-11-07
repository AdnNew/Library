package com.library.client.exception;

public enum ClientError {

    CLIENT_IS_ALREADY_EXISTS("Client is already exists in database"),
    CLIENT_IS_NOT_EXISTS("Client is not exists in database"),
    CLIENT_EMAIL_IS_NOT_UNAVAILABLE("Client's email is not unavailable"),
    CLIENT_STATUS_IS_ALREADY_INACTIVE("Client's status is already inactive");

    private String message;

    ClientError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
