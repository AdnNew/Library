package com.library.book.controller;

import com.library.book.model.Book;
import com.library.book.service.BookService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    Book addBook(@RequestBody @Valid Book book) {
        return bookService.addBook(book);
    }

    @GetMapping
    List<Book> getBooks() {
        return bookService.getBooks();
    }

    @GetMapping("/{bookId}")
    Book getBook(@PathVariable String bookId) {
        return bookService.getBook(bookId);
    }

    @PutMapping("/{bookId}")
    Book putBook(@PathVariable String bookId, @RequestBody @Valid Book book) {
        return bookService.putBook(bookId, book);
    }

    @PatchMapping("/{bookId}")
    Book patchBook(@PathVariable String bookId, @RequestBody Book book) {
        return bookService.patchBook(bookId, book);
    }

    @DeleteMapping("/{bookId}")
    void deleteBook(@PathVariable String bookId) {
        bookService.deleteBook(bookId);
    }

    @PostMapping("/{bookId}/add/client/{clientId}")
    void borrowBook(@PathVariable String bookId, @PathVariable Long clientId) {
        bookService.borrowBook(bookId, clientId);
    }

    @PostMapping("/{bookId}/remove/client/{clientId}")
    void returnBook(@PathVariable String bookId, @PathVariable Long clientId) {
        bookService.returnBook(bookId, clientId);
    }
}
