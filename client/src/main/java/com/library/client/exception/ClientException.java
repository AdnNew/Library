package com.library.client.exception;

public class ClientException extends RuntimeException {

    private ClientError clientError;

    public ClientException(ClientError clientError) {
        this.clientError = clientError;
    }

    public ClientError getClientError() {
        return clientError;
    }

}
