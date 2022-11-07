package com.library.client.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ClientExceptionHandler {

    @ExceptionHandler(value = ClientException.class)
    ResponseEntity<ErrorInfo> exceptionHandler(ClientException e) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        if (ClientError.CLIENT_IS_ALREADY_EXISTS.equals(e.getClientError())
                || ClientError.CLIENT_EMAIL_IS_NOT_UNAVAILABLE.equals(e.getClientError())
                || ClientError.CLIENT_STATUS_IS_ALREADY_INACTIVE.equals(e.getClientError()))
            httpStatus = HttpStatus.CONFLICT;
        if (ClientError.CLIENT_IS_NOT_EXISTS.equals(e.getClientError()))
            httpStatus = HttpStatus.NOT_FOUND;
        return ResponseEntity.status(httpStatus).body(new ErrorInfo(e.getClientError().getMessage()));
    }

}
