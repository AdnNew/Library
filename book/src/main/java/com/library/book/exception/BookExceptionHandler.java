package com.library.book.exception;

import feign.FeignException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BookExceptionHandler {

    @ExceptionHandler(value = BookException.class)
    ResponseEntity<ErrorInfo> exceptionHandler(BookException e) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        if (BookError.BOOK_IS_NOT_EXISTS.equals(e.getBookError()))
            httpStatus = HttpStatus.NOT_FOUND;
        if (BookError.BOOK_IS_ALREADY_UNAVAILABLE.equals(e.getBookError())
                || BookError.CLIENT_IS_ALREADY_ENROLLED.equals(e.getBookError()))
            httpStatus = HttpStatus.CONFLICT;
        if (BookError.CLIENT_DO_NOT_BORROW_A_BOOK.equals(e.getBookError())
                || BookError.BOOK_IS_ALREADY_EXISTS.equals(e.getBookError()))
            httpStatus = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(httpStatus).body(new ErrorInfo(e.getBookError().getMessage()));
    }

    @ExceptionHandler(value = FeignException.class)
    ResponseEntity<?> feignExceptionHandler(FeignException e) {
        return ResponseEntity.status(e.status()).body(new JSONObject(e.contentUTF8()).toMap());
    }
}
