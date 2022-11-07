package com.library.book.service;

import com.library.book.exception.BookError;
import com.library.book.exception.BookException;
import com.library.book.model.Book;
import com.library.book.model.Borrower;
import com.library.book.model.dto.ClientDto;
import com.library.book.model.dto.NotificationDto;
import com.library.book.repository.BookRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final FeignClientService feignClientService;
    private final RabbitTemplate rabbitTemplate;

    public BookServiceImpl(BookRepository bookRepository, FeignClientService feignClientService, RabbitTemplate rabbitTemplate) {
        this.bookRepository = bookRepository;
        this.feignClientService = feignClientService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public Book addBook(Book book) {
        if (bookRepository.existsByTitle(book.getTitle()) && bookRepository.existsByAuthor(book.getAuthor()))
            throw new BookException(BookError.BOOK_IS_ALREADY_EXISTS);
        return bookRepository.save(book);
    }

    @Override
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBook(String bookId) {
        return bookRepository.findById(bookId).orElseThrow(() -> new BookException(BookError.BOOK_IS_NOT_EXISTS));
    }

    @Override
    public Book putBook(String bookId, Book book) {
        Book bookFromDb = getBook(bookId);
        setBook(book, bookFromDb);
        return bookRepository.save(bookFromDb);
    }

    @Override
    public Book patchBook(String bookId, Book book) {
        Book bookFromDb = getBook(bookId);
        checkExistsBook(book, bookFromDb);
        return bookRepository.save(bookFromDb);
    }

    @Override
    public void deleteBook(String bookId) {
        Book book = getBook(bookId);
        validateBook(book);
        book.setQuantityInStock(0);
        bookRepository.save(book);
    }

    @Override
    public void borrowBook(String bookId, Long clientId) {
        Book book = getBook(bookId);
        validateBook(book);
        ClientDto clientDto = feignClientService.getClientById(clientId);
        validateClientToSave(book, clientDto);
        addBorrowerToList(book, clientDto);
        bookRepository.save(book);
    }

    @Override
    public void returnBook(String bookId, Long clientId) {
        Book book = getBook(bookId);
        ClientDto clientDto = feignClientService.getClientById(clientId);
        validateClientToRemove(book, clientDto);
        int clientNumber = findClient(book, clientDto);
        createAndSendNotification(book, clientNumber);
        removeClient(book, clientNumber);
        bookRepository.save(book);
    }

    private void createAndSendNotification(Book book, int clientNumber) {
        NotificationDto notification = NotificationDto.builder()
                .title(book.getTitle())
                .rating(book.getRating())
                .firstName(book.getBorrowerList().get(clientNumber).getFirstName())
                .email(book.getBorrowerList().get(clientNumber).getEmail())
                .build();
        rabbitTemplate.convertAndSend("FirstQueue", notification);
    }

    private static void removeClient(Book book, int clientNumber) {
        book.getBorrowerList().remove(clientNumber);
        book.setQuantityInStock(book.getQuantityInStock() + 1);
    }

    private static int findClient(Book book, ClientDto clientDto) {
        int clientNumber = 0;
        for (int i = 0; i < book.getBorrowerList().size(); i++) {
            if (book.getBorrowerList().get(i).getEmail().equals(clientDto.getEmail()))
                clientNumber = i;
        }

        return clientNumber;
    }

    private static void validateClientToRemove(Book book, ClientDto clientDto) {
        if (!book.getBorrowerList().stream().anyMatch(borrower -> clientDto.getEmail().equals(borrower.getEmail())))
            throw new BookException(BookError.CLIENT_DO_NOT_BORROW_A_BOOK);
    }

    private static void addBorrowerToList(Book book, ClientDto clientDto) {
        book.setQuantityInStock(book.getQuantityInStock() - 1);
        book.getBorrowerList().add(new Borrower(clientDto.getEmail(), clientDto.getFirstName(), clientDto.getLastName()));
    }

    private static void validateClientToSave(Book book, ClientDto clientDto) {
        if (book.getBorrowerList().stream().anyMatch(borrower -> clientDto.getEmail().equals(borrower.getEmail())))
            throw new BookException(BookError.CLIENT_IS_ALREADY_ENROLLED);
    }

    private static void validateBook(Book book) {
        if (book.getQuantityInStock().equals(0))
            throw new BookException(BookError.BOOK_IS_ALREADY_UNAVAILABLE);
    }

    private static void checkExistsBook(Book book, Book bookFromDb) {
        if (!ObjectUtils.isEmpty(book.getAuthor()))
            bookFromDb.setAuthor(book.getAuthor());
        if (!ObjectUtils.isEmpty(book.getTitle()))
            bookFromDb.setTitle(book.getTitle());
        if (!ObjectUtils.isEmpty(book.getRating()))
            bookFromDb.setRating(book.getRating());
        if (!ObjectUtils.isEmpty(book.getPublicationYear()))
            bookFromDb.setPublicationYear(book.getPublicationYear());
        if (!ObjectUtils.isEmpty(book.getQuantityInStock()))
            bookFromDb.setQuantityInStock(book.getQuantityInStock());
    }

    private static void setBook(Book book, Book bookFromDb) {
        bookFromDb.setAuthor(book.getAuthor());
        bookFromDb.setTitle(book.getTitle());
        bookFromDb.setRating(book.getRating());
        bookFromDb.setPublicationYear(book.getPublicationYear());
        bookFromDb.setQuantityInStock(book.getQuantityInStock());
    }
}
