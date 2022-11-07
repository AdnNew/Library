package com.library.book.service;

import com.library.book.model.Book;

import java.util.List;

public interface BookService {

    Book addBook(Book book);

    List<Book> getBooks();

    Book getBook (String bookId);

    Book putBook(String bookId, Book book);

    Book patchBook(String bookId, Book book);

    void deleteBook(String bookId);

    void borrowBook(String bookId, Long clientId);

    void returnBook(String bookId, Long clientId);
}
