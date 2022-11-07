package com.library.book.exception;

public enum BookError {

    BOOK_IS_NOT_EXISTS("Book is not exists in database"),
    BOOK_IS_ALREADY_UNAVAILABLE("Book is already unavailable"),
    CLIENT_IS_ALREADY_ENROLLED("Client is already enrolled"),
    CLIENT_DO_NOT_BORROW_A_BOOK("Client don't borrow a book"),
    BOOK_IS_ALREADY_EXISTS("Book is already exists in database");

    private String message;

    BookError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
