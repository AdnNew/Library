package com.library.book.repository;

import com.library.book.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {

    boolean existsByTitle(String title);

    boolean existsByAuthor(String author);
}
