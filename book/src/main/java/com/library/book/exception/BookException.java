package com.library.book.exception;

public class BookException extends RuntimeException {

    private BookError bookError;

    public BookException(BookError bookError) {
        this.bookError = bookError;
    }

    public BookError getBookError() {
        return bookError;
    }

}
